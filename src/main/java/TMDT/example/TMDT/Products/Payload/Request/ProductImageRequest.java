package TMDT.example.TMDT.Products.Payload.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageRequest {
    private Long idProduct;
    private boolean isThumbnail;
    private int sortOrder;
}
