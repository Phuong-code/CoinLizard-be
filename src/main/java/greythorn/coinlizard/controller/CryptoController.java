package greythorn.coinlizard.controller;

import greythorn.coinlizard.dto.CoinDetailsResponse;
import greythorn.coinlizard.model.Crypto;
import greythorn.coinlizard.model.PriceData;
import greythorn.coinlizard.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {
    private final CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService){
        this.cryptoService = cryptoService;
    }

    @GetMapping("/all-coins-id")
    public ResponseEntity<List<Crypto>> getAll() {
        List<Crypto> cryptos = cryptoService.getAllCoins();
        return ResponseEntity.ok(cryptos);
    }

    @GetMapping("/all-coins")
    public ResponseEntity<List<CoinDetailsResponse>> getAllCoins() {
        List<CoinDetailsResponse> coins = cryptoService.getAllCoinDetails();
        return ResponseEntity.ok(coins);
    }

    @GetMapping("/get24h/{symbol}")
    public ResponseEntity<Optional<PriceData>> get24h(@PathVariable String symbol) {
        Optional<PriceData> priceData = cryptoService.get24h(symbol);
        return ResponseEntity.ok(priceData);
    }

    @GetMapping("/coin/{id}")
    public ResponseEntity<Optional<CoinDetailsResponse>> getOneCoin(@PathVariable UUID id) {
        Optional<CoinDetailsResponse> coin = cryptoService.getCoinById(id);
        return ResponseEntity.ok(coin);
    }
}
