package TMDT.example.TMDT.Products.ServiceImp;

import TMDT.example.TMDT.Exception.ResourceNotFoundException;
import TMDT.example.TMDT.Products.Entity.VariantAtrributeEntity;
import TMDT.example.TMDT.Products.Payload.Mapper.VariantAttributeMapper;
import TMDT.example.TMDT.Products.Payload.Reponse.VariantAttributeReponse;
import TMDT.example.TMDT.Products.Payload.Request.VariantAttributeRequest;
import TMDT.example.TMDT.Products.Repo.VariantAttributeRepo;
import TMDT.example.TMDT.Products.Service.VariantAttributeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class VariantAttributeServiceImp  implements VariantAttributeService {
    private final VariantAttributeRepo  variantAttributeRepo;
    private final VariantAttributeMapper mapper;
    @Override
    public void createVariantAttibute(VariantAttributeRequest request) {
        VariantAtrributeEntity entity = VariantAtrributeEntity.builder()
                .attributeType(request.getAttributeType())
                .build();
        variantAttributeRepo.save(entity);
    }

    @Override
    public void updateVariantAttibute(Long id, VariantAttributeRequest request) {
        VariantAtrributeEntity entity = variantAttributeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Variant Attribute Not Found"));
        if(request.getAttributeType()!=null){
            entity.setAttributeType(request.getAttributeType());
        }
        variantAttributeRepo.save(entity);
    }

    @Override
    public void deleteVariantAttibute(Long id) {
        if(!variantAttributeRepo.existsById(id)){
            throw new ResourceNotFoundException("Variant Attribute Not Found");
        }
        variantAttributeRepo.deleteById(id);
    }

    @Override
    public List<VariantAttributeReponse> getAllVariantAttribute() {
        List<VariantAtrributeEntity> list = variantAttributeRepo.findAll();
        return mapper.toDTOList(list) ;
    }
}
