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

/**
 * Controller for handling price data requests related to cryptocurrencies.
 * This controller provides endpoints for retrieving historical price data of coins.
 */
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

    /**
     * Endpoint to retrieve price chart data of a specific coin.
     * This method handles GET requests to "/{id}/days={days}" and returns a map of price chart data.
     *
     * @param id The UUID of the coin.
     * @param days The number of days for which the price data is requested.
     * @return A ResponseEntity containing a map of price chart data and the HTTP status.
     */
    @GetMapping("/{id}/days={days}")
    public ResponseEntity<Map<String, Object>> getCoinPriceChart(
            @PathVariable UUID id,
            @PathVariable int days) {
        Optional<CoinDetailsResponse> coinDetailsResponseOptional = cryptoService.getCoinById(id);
        List<PriceChartResponse> priceChart = null;
        if (coinDetailsResponseOptional.isPresent()) {
            Crypto crypto = new Crypto();
            crypto.setId(coinDetailsResponseOptional.get().getId());
            crypto.setSymbol(coinDetailsResponseOptional.get().getSymbol());
            crypto.setName(coinDetailsResponseOptional.get().getName());
            crypto.setImage(coinDetailsResponseOptional.get().getImage());
            // Get the price chart data for the specified crypto and time frame
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
