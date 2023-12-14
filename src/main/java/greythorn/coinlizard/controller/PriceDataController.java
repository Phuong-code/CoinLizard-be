package greythorn.coinlizard.controller;

import greythorn.coinlizard.service.PriceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceDataController {
    private final PriceDataService priceDataService;

    @Autowired
    public PriceDataController(PriceDataService priceDataService){
        this.priceDataService = priceDataService;
    }
}
