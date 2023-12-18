package greythorn.coinlizard.controller;

import greythorn.coinlizard.dto.CoinDetailsResponse;
import greythorn.coinlizard.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for handling cryptocurrency-related requests.
 * This controller provides endpoints for retrieving information about different coins.
 */
@RestController
@RequestMapping("/api/crypto")
public class CryptoController {
    private final CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService){
        this.cryptoService = cryptoService;
    }

    /**
     * Endpoint to retrieve a list of all coins.
     * This method handles GET requests to "/all-coins" and returns a list of CoinDetailsResponse.
     *
     * @return A ResponseEntity containing the list of all coins and the HTTP status.
     */
    @GetMapping("/all-coins")
    public ResponseEntity<List<CoinDetailsResponse>> getAllCoins() {
        List<CoinDetailsResponse> coins = cryptoService.getAllCoinDetails();
        return ResponseEntity.ok(coins);
    }

    /**
     * Endpoint to retrieve details of a specific coin by its ID.
     * This method handles GET requests to "/coin/{id}" and returns the details of a specific coin.
     *
     * @param id The UUID of the coin to retrieve.
     * @return A ResponseEntity containing the coin details if found, along with the HTTP status.
     */
    @GetMapping("/coin/{id}")
    public ResponseEntity<Optional<CoinDetailsResponse>> getOneCoin(@PathVariable UUID id) {
        Optional<CoinDetailsResponse> coin = cryptoService.getCoinById(id);
        return ResponseEntity.ok(coin);
    }
}
