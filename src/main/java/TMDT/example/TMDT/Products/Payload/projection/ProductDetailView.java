package TMDT.example.TMDT.Products.Payload.projection;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


public interface ProductDetailView {
    Long getIdProduct();
    String getNameProduct();
    String getProductImage();
    String getDescription();
    String getNameCategory();
    Long getVariantId();
    String getImageVariant();
    Integer getVariantQuantity();
    BigDecimal getPriceVariant();
    String getAttributeType();
    String getValue();
}
