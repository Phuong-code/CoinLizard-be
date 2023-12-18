package greythorn.coinlizard.service;

import greythorn.coinlizard.dto.PriceChartResponse;
import greythorn.coinlizard.model.Crypto;
import greythorn.coinlizard.repository.PriceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceDataService {
    private final PriceDataRepository priceDataRepository;

    @Autowired
    public PriceDataService(PriceDataRepository priceDataRepository){
        this.priceDataRepository = priceDataRepository;
    }

    /**
     * Retrieves a list of price chart responses for a given cryptocurrency over a specified number of days.
     *
     * @param crypto The cryptocurrency entity for which the price chart is required.
     * @param days   The number of days to retrieve the price data for.
     * @return A list of PriceChartResponse, each representing a day's price data.
     */
    public List<PriceChartResponse> getCoinPriceChart(Crypto crypto, int days) {
        // Retrieve and map the price data to a list of PriceChartResponse
        List<PriceChartResponse> chartResponses = priceDataRepository.getCoinPriceChart(crypto, days).stream()
                .map(priceData -> new PriceChartResponse(
                        priceData.getDate(),
                        priceData.getClose()
                ))
                .collect(Collectors.toList());
        // Reverse the list to get the prices in chronological order
        Collections.reverse(chartResponses);
        return chartResponses;
    }


}
