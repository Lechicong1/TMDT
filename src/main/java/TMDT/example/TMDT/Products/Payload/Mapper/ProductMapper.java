package TMDT.example.TMDT.Products.Payload.Mapper;

import TMDT.example.TMDT.Enums.Folder;
import TMDT.example.TMDT.Products.Entity.CategoryEntity;
import TMDT.example.TMDT.Products.Entity.ProductEntity;
import TMDT.example.TMDT.Products.Payload.Reponse.CategoryReponse;
import TMDT.example.TMDT.Products.Payload.Reponse.ProductDetailDTO;
import TMDT.example.TMDT.Products.Payload.Reponse.ProductReponse;
import TMDT.example.TMDT.Products.Payload.Request.ProductRequest;
import TMDT.example.TMDT.Products.Payload.projection.ProductDetailView;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    @Value("${app.base-url}")
    protected String baseUrl;
   public abstract ProductEntity toEntity(ProductRequest productRequest);
   public abstract ProductReponse toDTO(ProductEntity product);
   public abstract List<ProductReponse> toDTOList(List<ProductEntity> products) ;
    @AfterMapping
    protected void enrich(@MappingTarget ProductReponse dto, ProductEntity entity) {
        if (entity.getProductImage() != null) {
            dto.setProductImage(baseUrl + "/Upload/" + Folder.ProductImage.name() + "/" + entity.getProductImage());
        }
        dto.setShopName(entity.getShop().getShopName());
        dto.setNameCategory(entity.getCategory().getName());
    }
    public abstract List<ProductDetailDTO> toDTODetail(List<ProductDetailView> view);
    @AfterMapping
    protected void enrich1(@MappingTarget ProductDetailDTO dto, ProductDetailView entity) {
        if (entity.getImageVariant() != null) {
            dto.setVariantImage(baseUrl + "/Upload/" + Folder.ProductVariantImage.name() + "/" + entity.getImageVariant());
        }
        if (entity.getProductImage() != null) {
            dto.setProductImage(baseUrl + "/Upload/" + Folder.ProductImage.name() + "/" + entity.getProductImage());
        }
    }
}
