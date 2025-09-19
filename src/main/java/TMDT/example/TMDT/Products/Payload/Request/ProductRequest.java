package TMDT.example.TMDT.Products.Payload.Request;

import TMDT.example.TMDT.Products.Payload.Reponse.VariantAttributeReponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private boolean active;
    private Long idCategory;
//    private List<ProductVariantRequest> productVariant;
//    private List<ProductImageRequest> productImage;
}
