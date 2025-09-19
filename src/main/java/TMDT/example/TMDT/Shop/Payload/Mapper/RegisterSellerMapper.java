package TMDT.example.TMDT.Shop.Payload.Mapper;

import TMDT.example.TMDT.Delivery.DTO.Reponse.DeliveryReponse;
import TMDT.example.TMDT.Delivery.Entity.DeliveryServiceEntity;
import TMDT.example.TMDT.Enums.Folder;
import TMDT.example.TMDT.Shop.Entity.SellerRegisterEntity;
import TMDT.example.TMDT.Shop.Payload.Reponse.RegisterSellerReponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RegisterSellerMapper {
    @Value("${app.base-url}")
    protected String baseUrl;
    @AfterMapping
    protected void enrich(@MappingTarget RegisterSellerReponse dto, SellerRegisterEntity entity) {
        if (entity.getIdentityDocumentUrl() != null) {
            dto.setIdentityDocumentUrl(baseUrl + "/Upload/" + Folder.SellerRegiter.name() + "/" + entity.getIdentityDocumentUrl());
        }
    }
    @Mapping(source = "user.username", target = "username")
    public abstract RegisterSellerReponse toDTO(SellerRegisterEntity entity) ;
    @Mapping(source = "user.username", target = "username")
    public abstract List<RegisterSellerReponse> toDTOList(List<SellerRegisterEntity> list) ;
}
