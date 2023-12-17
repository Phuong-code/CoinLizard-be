package greythorn.coinlizard.repository;

import greythorn.coinlizard.model.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CryptoRepository extends JpaRepository<Crypto, UUID> {

    Crypto findBySymbol(String symbol);
}
