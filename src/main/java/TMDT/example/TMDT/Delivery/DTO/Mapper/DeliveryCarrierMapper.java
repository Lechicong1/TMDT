package TMDT.example.TMDT.Delivery.DTO.Mapper;

import TMDT.example.TMDT.Delivery.DTO.Reponse.DeliveryReponse;
import TMDT.example.TMDT.Delivery.Entity.DeliveryServiceEntity;
import TMDT.example.TMDT.Enums.Folder;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DeliveryCarrierMapper {

    @Value("${app.base-url}")
    protected String baseUrl;

    public abstract DeliveryReponse toDTO(DeliveryServiceEntity entity);
    public abstract List<DeliveryReponse> toDTOList(List<DeliveryServiceEntity> deliveryServiceEntityList);

    @AfterMapping
    protected void enrich(@MappingTarget DeliveryReponse dto, DeliveryServiceEntity entity) {
        if (entity.getLogoUrl() != null) {
            dto.setLogoUrl(baseUrl + "/Upload/" + Folder.logoDeliveryCarrier.name() + "/" + entity.getLogoUrl());
        }
    }
}
