package TMDT.example.TMDT.Products.Payload.Reponse;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductReponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String productImage;
    private String shopName;
    private String nameCategory;
}
