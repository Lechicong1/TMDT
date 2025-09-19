package TMDT.example.TMDT.Products.Service;

import TMDT.example.TMDT.Products.Payload.Reponse.VariantAttributeReponse;
import TMDT.example.TMDT.Products.Payload.Request.VariantAttributeRequest;

import java.util.List;

public interface VariantAttributeService {
    void createVariantAttibute(VariantAttributeRequest request);
    void updateVariantAttibute(Long id ,VariantAttributeRequest request);
    void deleteVariantAttibute(Long id);
    List<VariantAttributeReponse > getAllVariantAttribute() ;
}
