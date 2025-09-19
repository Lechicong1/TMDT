package TMDT.example.TMDT.Products.Payload.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Variant_Attibute_Value_Request {

    private Long attributeId;
    private String value;
    private String imageVariant;
}
