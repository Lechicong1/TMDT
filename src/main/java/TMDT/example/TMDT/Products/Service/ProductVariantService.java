package TMDT.example.TMDT.Products.Service;

import TMDT.example.TMDT.Products.Entity.ProductVariantEntity;
import TMDT.example.TMDT.Products.Payload.Request.ProductVariantRequest;

import java.util.List;

public interface ProductVariantService {
    void createProductVariant(Long productId , List<ProductVariantRequest > productVariantRequest);
    void updateProductVariant( Long id , ProductVariantRequest  productVariantRequest);
    void deleteProductVariant(Long id);
    ProductVariantEntity getProductVariant(Long idProduct);
}
