package greythorn.coinlizard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinDetailsResponse {
    private UUID id;
    private String name;
    private String image;
    private String symbol;
    private BigDecimal currentPrice;
    private BigDecimal priceChangePercentage24h;
    private BigDecimal priceChangePercentage7d;
    private BigDecimal priceChangePercentage30d;
    private Long volumn24h;
    private Long marketCap;
}
