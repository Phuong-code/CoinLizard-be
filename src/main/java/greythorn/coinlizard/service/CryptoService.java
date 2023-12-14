package greythorn.coinlizard.service;

import greythorn.coinlizard.repository.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptoService {
    private final CryptoRepository cryptoRepository;

    @Autowired
    public CryptoService(CryptoRepository cryptoRepository){
        this.cryptoRepository = cryptoRepository;
    }
}
