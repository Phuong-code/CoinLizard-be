package greythorn.coinlizard.repository;

import greythorn.coinlizard.model.Crypto;
import greythorn.coinlizard.model.PriceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface PriceDataRepository extends JpaRepository<PriceData, UUID> {

    Optional<PriceData> findTopByCryptoOrderByDateDesc(Crypto crypto);

    @Query("SELECT pd FROM PriceData pd WHERE pd.crypto = :crypto AND pd.date = :targetDate")
    Optional<PriceData> findPriceDataByDate(Crypto crypto, Date targetDate);

    default Optional<PriceData> findPriceDataRelativeToLatest(Crypto crypto, int hoursBack) {
        return findTopByCryptoOrderByDateDesc(crypto).flatMap(latestData -> {
            Date latestDate = latestData.getDate();
            Date targetDate = new Date(latestDate.getTime() - (long) hoursBack * 60 * 60 * 1000);
            return findPriceDataByDate(crypto, targetDate);
        });
    }
}
