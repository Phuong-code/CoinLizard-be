package greythorn.coinlizard.controller;

import greythorn.coinlizard.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoController {
    private final CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService){
        this.cryptoService = cryptoService;
    }
}
