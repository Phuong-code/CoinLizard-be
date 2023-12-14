package greythorn.coinlizard.service;

import greythorn.coinlizard.model.PriceData;
import greythorn.coinlizard.repository.CryptoRepository;
import greythorn.coinlizard.repository.PriceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceDataService {
    private final PriceDataRepository priceDataRepository;

    @Autowired
    public PriceDataService(PriceDataRepository priceDataRepository){
        this.priceDataRepository = priceDataRepository;
    }
}
