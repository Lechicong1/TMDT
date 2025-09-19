package TMDT.example.TMDT.Products.Payload.Mapper;

import TMDT.example.TMDT.Products.Entity.VariantAtrributeEntity;
import TMDT.example.TMDT.Products.Payload.Reponse.VariantAttributeReponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VariantAttributeMapper {
    List<VariantAttributeReponse> toDTOList(List<VariantAtrributeEntity >variantAtrributeEntity);
}
