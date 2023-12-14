package greythorn.coinlizard.service;

import greythorn.coinlizard.model.PriceData;
import greythorn.coinlizard.repository.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceDataService {
    private final PriceData priceData;

    @Autowired
    public PriceDataService(PriceData priceData){
        this.priceData = priceData;
    }
}
