package TMDT.example.TMDT.Shop.Payload.Mapper;

import TMDT.example.TMDT.Delivery.DTO.Reponse.DeliveryReponse;
import TMDT.example.TMDT.Delivery.Entity.DeliveryServiceEntity;
import TMDT.example.TMDT.Enums.Folder;
import TMDT.example.TMDT.Shop.Entity.ShopEntity;
import TMDT.example.TMDT.Shop.Payload.Reponse.ShopReponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ShopMapper {
    @Value("${app.base-url}")
    protected String baseUrl;
    public abstract List<ShopReponse> toDTOList(List<ShopEntity> listentity);
    public abstract ShopReponse toDTO(ShopEntity entity);
    @AfterMapping
    protected void enrich(@MappingTarget ShopReponse dto, ShopEntity entity) {
        if (entity.getShopLogoUrl() != null) {
            dto.setShopLogoUrl(baseUrl + "/Upload/" + Folder.LogoShop.name() + "/" + entity.getShopLogoUrl());
        }
    }
}
