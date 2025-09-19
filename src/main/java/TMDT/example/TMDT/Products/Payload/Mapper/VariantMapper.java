package TMDT.example.TMDT.Products.Payload.Mapper;

import TMDT.example.TMDT.Products.Entity.ProductVariantEntity;
import TMDT.example.TMDT.Products.Payload.Reponse.ProductDetailDTO;
import TMDT.example.TMDT.Products.Payload.Request.ProductVariantRequest;
import TMDT.example.TMDT.Products.Payload.projection.ProductDetailView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VariantMapper {
    ProductVariantEntity toEntity(ProductVariantRequest request);

}
