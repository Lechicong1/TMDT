package TMDT.example.TMDT.Products.Payload.Request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductVariantRequest {
    private Long idProduct;
    private BigDecimal price;
    private int quantity;
    private String sku;
//    private BigDecimal weight;
//    private BigDecimal length;
//    private BigDecimal width;
//    private BigDecimal height;
//    private String image;
    private List<Variant_Attibute_Value_Request> variant_attibute_value;
}
