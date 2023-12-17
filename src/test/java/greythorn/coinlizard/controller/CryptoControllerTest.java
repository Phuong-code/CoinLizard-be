package greythorn.coinlizard.controller;
import greythorn.coinlizard.dto.CoinDetailsResponse;
import greythorn.coinlizard.service.CryptoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;




@WebMvcTest(CryptoController.class)
public class CryptoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CryptoService cryptoService;

    @Test
    public void testGetAllCoins() throws Exception {
        CoinDetailsResponse coin1 = new CoinDetailsResponse();
        coin1.setId(UUID.randomUUID());
        coin1.setName("Bitcoin");
        coin1.setSymbol("BTC");
        coin1.setImage("imageURL");
        coin1.setMarketCap(1000L);
        coin1.setCurrentPrice(BigDecimal.valueOf(1000));
        coin1.setVolumn24h(1000L);
        coin1.setPriceChangePercentage7d(BigDecimal.valueOf(10.10));
        coin1.setPriceChangePercentage24h(BigDecimal.valueOf(10.10));
        coin1.setPriceChangePercentage30d(BigDecimal.valueOf(10.10));
        coin1.setCurrentPrice(BigDecimal.valueOf(1000));
        CoinDetailsResponse coin2 = new CoinDetailsResponse();
        coin2.setId(UUID.randomUUID());
        coin2.setName("Ethereum");
        coin2.setSymbol("ETH");
        coin2.setImage("imageURL");
        coin2.setMarketCap(1000L);
        coin2.setCurrentPrice(BigDecimal.valueOf(1000));
        coin2.setVolumn24h(1000L);
        coin2.setPriceChangePercentage7d(BigDecimal.valueOf(10.10));
        coin2.setPriceChangePercentage24h(BigDecimal.valueOf(10.10));
        coin2.setPriceChangePercentage30d(BigDecimal.valueOf(10.10));
        coin2.setCurrentPrice(BigDecimal.valueOf(1000));

        List<CoinDetailsResponse> allCoins = Arrays.asList(coin1, coin2);

        given(cryptoService.getAllCoinDetails()).willReturn(allCoins);

        mockMvc.perform(get("/api/price-data/all-coins")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(allCoins.size())))
                .andExpect(jsonPath("$[0].name", is("Bitcoin")))
                .andExpect(jsonPath("$[1].name", is("Ethereum")));
    }


}
