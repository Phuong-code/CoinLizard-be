package greythorn.coinlizard.service;

import greythorn.coinlizard.dto.CoinDetailsResponse;
import greythorn.coinlizard.model.Crypto;
import greythorn.coinlizard.model.PriceData;
import greythorn.coinlizard.repository.CryptoRepository;
import greythorn.coinlizard.repository.PriceDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptoServiceTest {
    @InjectMocks
    private CryptoService cryptoService;

    @Mock
    private CryptoRepository cryptoRepository;

    @Mock
    private PriceDataRepository priceDataRepository;

    @Test
    public void testGetAllCoinDetailsNoPriceData() {
        Crypto crypto = new Crypto();
        crypto.setId(UUID.randomUUID());
        crypto.setName("Bitcoin");
        crypto.setSymbol("BTC");
        crypto.setImage("BTC_images");
        when(cryptoRepository.findAll()).thenReturn(Collections.singletonList(crypto));
        when(priceDataRepository.findTopByCryptoOrderByDateDesc(any())).thenReturn(Optional.empty());

        List<CoinDetailsResponse> responses = cryptoService.getAllCoinDetails();

        assertNotNull(responses);
    }

    @Test
    public void testGetAllCoinDetailsWithPriceData() {
        Crypto crypto = new Crypto();
        crypto.setId(UUID.randomUUID());
        crypto.setName("Bitcoin");
        crypto.setSymbol("BTC");
        crypto.setImage("BTC_images");
        PriceData priceData = new PriceData();
        priceData.setId(UUID.randomUUID());
        priceData.setCrypto(crypto);
        priceData.setHigh(BigDecimal.valueOf(1000));
        priceData.setLow(BigDecimal.valueOf(1000));
        priceData.setClose(BigDecimal.valueOf(1000));
        priceData.setMarketcap(1000L);
        priceData.setDate(new Date());
        when(cryptoRepository.findAll()).thenReturn(Collections.singletonList(crypto));
        when(priceDataRepository.findTopByCryptoOrderByDateDesc(any())).thenReturn(Optional.of(priceData));

        List<CoinDetailsResponse> responses = cryptoService.getAllCoinDetails();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertNotNull(responses.getFirst().getCurrentPrice());
    }
}
