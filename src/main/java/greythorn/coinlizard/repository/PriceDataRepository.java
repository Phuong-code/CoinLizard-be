package greythorn.coinlizard.repository;

import greythorn.coinlizard.model.PriceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PriceDataRepository extends JpaRepository<PriceData, UUID> {
}
