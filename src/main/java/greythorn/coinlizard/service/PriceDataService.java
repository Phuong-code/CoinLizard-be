package greythorn.coinlizard.service;

import greythorn.coinlizard.dto.CoinDetailsResponse;
import greythorn.coinlizard.dto.PriceChartResponse;
import greythorn.coinlizard.model.Crypto;
import greythorn.coinlizard.model.PriceData;
import greythorn.coinlizard.repository.CryptoRepository;
import greythorn.coinlizard.repository.PriceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PriceDataService {
    private final PriceDataRepository priceDataRepository;

    @Autowired
    public PriceDataService(PriceDataRepository priceDataRepository){
        this.priceDataRepository = priceDataRepository;
    }


    public List<PriceChartResponse> getCoinPriceChart(Crypto crypto, int days) {
        List<PriceChartResponse> chartResponses = priceDataRepository.getCoinPriceChart(crypto, days).stream()
                .map(priceData -> new PriceChartResponse(
                        priceData.getDate(),
                        priceData.getClose()
                ))
                .collect(Collectors.toList());

        Collections.reverse(chartResponses);
        return chartResponses;
    }


}
