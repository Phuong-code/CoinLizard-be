package greythorn.coinlizard.service;

import greythorn.coinlizard.model.Crypto;
import greythorn.coinlizard.model.PriceData;
import greythorn.coinlizard.repository.CryptoRepository;
import greythorn.coinlizard.repository.PriceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import greythorn.coinlizard.dto.CoinDetailsResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CryptoService {
    @Autowired
    private CryptoRepository cryptoRepository;
    @Autowired
    private PriceDataRepository priceDataRepository;

    public List<Crypto> getAllCoins(){
        return cryptoRepository.findAll();
    }

    public Optional<PriceData> get24h(String symbol){
        Crypto crypto = cryptoRepository.findBySymbol(symbol);
        return priceDataRepository.findPriceDataRelativeToLatest(crypto, 24);
    }

    public Optional<CoinDetailsResponse> getCoinById(UUID id){
        Optional<Crypto> cryptoOptional =  cryptoRepository.findById(id);
        if (cryptoOptional.isPresent()) {
            Crypto crypto = cryptoOptional.get();
            Optional<PriceData> latestPriceDataOptional = priceDataRepository.findTopByCryptoOrderByDateDesc(crypto);
            if (latestPriceDataOptional.isPresent()) {
                PriceData latestpriceData = latestPriceDataOptional.get();
                return Optional.of(new CoinDetailsResponse(
                        crypto.getId(),
                        crypto.getName(),
                        crypto.getImage(),
                        crypto.getSymbol(),
                        latestpriceData.getClose(),
                        null,
                        null,
                        null,
                        null,
                        latestpriceData.getMarketcap()
                ));
            }
        }
        return null;
    }

    public List<CoinDetailsResponse> getAllCoinDetails() {
        return cryptoRepository.findAll().stream().map(crypto -> {
            Optional<PriceData> latestPriceDataOptional = priceDataRepository.findTopByCryptoOrderByDateDesc(crypto);

            if (latestPriceDataOptional.isEmpty()) {
                // Handle the case when there's no price data available
                return new CoinDetailsResponse(
                        crypto.getId(),
                        crypto.getName(),
                        crypto.getImage(),
                        crypto.getSymbol(),
                        null, // Current price
                        null, // 24h price change
                        null, // 7d price change
                        null, //30d price change
                        null, // Volume
                        null  // Market cap
                );
            }

            PriceData latestPriceData = latestPriceDataOptional.get();
            BigDecimal currentPrice = latestPriceData.getClose();
            BigDecimal priceChange24h = calculatePriceChange(crypto, 24);
            BigDecimal priceChange7d = calculatePriceChange(crypto, 7 * 24);
            BigDecimal priceChange30d = calculatePriceChange(crypto, 30 * 24);

            return new CoinDetailsResponse(
                    crypto.getId(),
                    crypto.getName(),
                    crypto.getImage(),
                    crypto.getSymbol(),
                    currentPrice,
                    priceChange24h,
                    priceChange7d,
                    priceChange30d,
                    latestPriceData.getVolume(),
                    latestPriceData.getMarketcap()
            );
        })
        .filter(response -> response.getMarketCap() != null)
        .sorted(Comparator.comparing(CoinDetailsResponse::getMarketCap).reversed())
        .collect(Collectors.toList());
    }

    private BigDecimal calculatePriceChange(Crypto crypto, int hoursBack) {

        Optional<PriceData> pastPriceDataOptional = priceDataRepository.findPriceDataRelativeToLatest(crypto, hoursBack);

        if (pastPriceDataOptional.isPresent()) {
            PriceData pastPriceData = pastPriceDataOptional.get();
            Optional<PriceData> latestPriceDataOptional = priceDataRepository.findTopByCryptoOrderByDateDesc(crypto);

            if (latestPriceDataOptional.isPresent()) {
                PriceData latestPriceData = latestPriceDataOptional.get();
                BigDecimal pastPrice = pastPriceData.getClose();
                BigDecimal latestPrice = latestPriceData.getClose();

                if (pastPrice.compareTo(BigDecimal.ZERO) > 0) {
                    return latestPrice.subtract(pastPrice)
                            .divide(pastPrice,4, RoundingMode.DOWN)
                            .multiply(BigDecimal.valueOf(100))
                            .setScale(2, RoundingMode.DOWN);
                }
            }
        }
        return null;
    }
}
