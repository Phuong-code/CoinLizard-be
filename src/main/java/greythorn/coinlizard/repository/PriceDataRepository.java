package greythorn.coinlizard.repository;

import greythorn.coinlizard.model.Crypto;
import greythorn.coinlizard.model.PriceData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface PriceDataRepository extends JpaRepository<PriceData, UUID> {

    /**
     * Finds the latest PriceData entry for a given Crypto.
     *
     * @param crypto The crypto entity for which the latest price data is required.
     * @return An Optional containing the latest PriceData if available.
     */
    Optional<PriceData> findTopByCryptoOrderByDateDesc(Crypto crypto);

    /**
     * Custom query to find PriceData by Crypto and a specific date.
     *
     * @param crypto The crypto entity for which the price data is required.
     * @param targetDate The date for which the price data is required.
     * @return An Optional containing the PriceData if found for the specified date.
     */
    @Query("SELECT pd FROM PriceData pd WHERE pd.crypto = :crypto AND pd.date = :targetDate")
    Optional<PriceData> findPriceDataByDate(Crypto crypto, Date targetDate);

    /**
     * Finds PriceData relative to the latest entry, going back a certain number of hours.
     *
     * @param crypto The crypto entity for which the price data is required.
     * @param hoursBack The number of hours to go back from the latest data.
     * @return An Optional containing the PriceData if found.
     */
    default Optional<PriceData> findPriceDataRelativeToLatest(Crypto crypto, int hoursBack) {
        return findTopByCryptoOrderByDateDesc(crypto).flatMap(latestData -> {
            Date latestDate = latestData.getDate();
            Date targetDate = new Date(latestDate.getTime() - (long) hoursBack * 60 * 60 * 1000);
            return findPriceDataByDate(crypto, targetDate);
        });
    }

    /**
     * Finds a list of PriceData for a given Crypto, ordered by date in descending order.
     *
     * @param crypto The crypto entity for which the price data is required.
     * @param pageable A pageable object defining the page size and number.
     * @return A list of PriceData entries.
     */
    List<PriceData> findByCryptoOrderByDateDesc(Crypto crypto, Pageable pageable);

    /**
     * Retrieves a chart of coin prices over a specified number of days.
     *
     * @param crypto The crypto entity for which the chart data is required.
     * @param days The number of days for which the data is requested.
     * @return A list of PriceData representing the price chart.
     */
    default List<PriceData> getCoinPriceChart(Crypto crypto, int days) {
        Pageable limit = PageRequest.of(0, days);
        return findByCryptoOrderByDateDesc(crypto, limit);
    }
}
