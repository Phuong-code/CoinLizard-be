package greythorn.coinlizard.controller;

import greythorn.coinlizard.dto.CoinDetailsResponse;
import greythorn.coinlizard.dto.PriceChartResponse;
import greythorn.coinlizard.model.Crypto;
import greythorn.coinlizard.service.CryptoService;
import greythorn.coinlizard.service.PriceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/price-data")
public class PriceDataController {

    private final PriceDataService priceDataService;
    private final CryptoService cryptoService;
    @Autowired
    public PriceDataController(PriceDataService priceDataService, CryptoService cryptoService) {
        this.priceDataService = priceDataService;
        this.cryptoService = cryptoService;
    }


    @GetMapping("/{id}/days={days}")
    public ResponseEntity<Map<String, Object>> getCoinPriceChart(
            @PathVariable UUID id,
            @PathVariable int days) {
        // Assuming you have a method to retrieve the Crypto object using the id
        Optional<CoinDetailsResponse> coinDetailsResponseOptional = cryptoService.getCoinById(id);
        List<PriceChartResponse> priceChart = null;
        if (coinDetailsResponseOptional.isPresent()) {
            Crypto crypto = new Crypto();
            crypto.setId(coinDetailsResponseOptional.get().getId());
            crypto.setSymbol(coinDetailsResponseOptional.get().getSymbol());
            crypto.setName(coinDetailsResponseOptional.get().getName());
            crypto.setImage(coinDetailsResponseOptional.get().getImage());
            priceChart = priceDataService.getCoinPriceChart(crypto, days);
        }

        List<List<Object>> formattedPrices = new ArrayList<>();
        for (PriceChartResponse priceResponse : priceChart) {
            List<Object> pricePair = new ArrayList<>();
            pricePair.add(priceResponse.getDate().getTime()); // Convert Date to timestamp
            pricePair.add(priceResponse.getClose()); // Get the close price
            formattedPrices.add(pricePair);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("prices", formattedPrices);
        return ResponseEntity.ok(response);
    }
}
