package TMDT.example.TMDT.Products.Payload.Mapper;

import TMDT.example.TMDT.Enums.Folder;
import TMDT.example.TMDT.Products.Entity.CategoryEntity;
import TMDT.example.TMDT.Products.Payload.Reponse.CategoryReponse;
import TMDT.example.TMDT.Shop.Entity.ShopEntity;
import TMDT.example.TMDT.Shop.Payload.Reponse.ShopReponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CateGoryMapper {
    @Value("${app.base-url}")
    protected String baseUrl;
    public abstract List<CategoryReponse> toDTO(List<CategoryEntity> categories);
    public abstract CategoryReponse toDTO(CategoryEntity category);
    @AfterMapping
    protected void enrich(@MappingTarget CategoryReponse dto, CategoryEntity entity) {
        if (entity.getImageCategory() != null) {
            dto.setImageCategory(baseUrl + "/Upload/" + Folder.CategoryImage.name() + "/" + entity.getImageCategory());
        }
    }
}
