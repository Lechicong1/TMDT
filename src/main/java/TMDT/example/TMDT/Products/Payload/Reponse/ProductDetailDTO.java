package TMDT.example.TMDT.Products.Payload.Reponse;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDetailDTO {
    private Long idProduct;
    private String nameProduct;
    private String productImage;
    private String description;
    private String nameCategory;
    private String variantId;
    private String variantImage;
    private Long variantQuantity;
    private String attributeType;
    private String value ;
    private BigDecimal priceVariant;
}
