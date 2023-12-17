package greythorn.coinlizard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "price_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "crypto_id", referencedColumnName = "id")
    private Crypto crypto;

    private Date date;

    @Column(precision = 20, scale = 4)
    private BigDecimal high;

    @Column(precision = 20, scale = 4)
    private BigDecimal low;

    @Column(precision = 20, scale = 4)
    private BigDecimal open;

    @Column(precision = 20, scale = 4)
    private BigDecimal close;

    @Column(precision = 20, scale = 4)
    private Long volume;

    @Column(precision = 20, scale = 4)
    private Long marketcap;
}
