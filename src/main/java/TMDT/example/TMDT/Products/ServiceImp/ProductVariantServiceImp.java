package TMDT.example.TMDT.Products.ServiceImp;

import TMDT.example.TMDT.Enums.Folder;
import TMDT.example.TMDT.Exception.ResourceNotFoundException;
import TMDT.example.TMDT.Products.Entity.ProductEntity;
import TMDT.example.TMDT.Products.Entity.ProductVariantAttributeValueEntity;
import TMDT.example.TMDT.Products.Entity.ProductVariantEntity;
import TMDT.example.TMDT.Products.Entity.VariantAtrributeEntity;
import TMDT.example.TMDT.Products.Payload.Mapper.VariantMapper;
import TMDT.example.TMDT.Products.Payload.Request.ProductVariantRequest;
import TMDT.example.TMDT.Products.Payload.Request.VariantAttributeRequest;
import TMDT.example.TMDT.Products.Payload.Request.Variant_Attibute_Value_Request;
import TMDT.example.TMDT.Products.Repo.*;
import TMDT.example.TMDT.Products.Service.ProductVariantService;
import TMDT.example.TMDT.Utils.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductVariantServiceImp implements ProductVariantService {
    private final ProductRepo productRepo;
    private final ProductVariantRepo productVariantRepo;
    private final VariantMapper variantMapper;
    private final VariantAttributeRepo variantAttributeRepo;
    private final VarianAttribueValueRepo varianAttribueValueRepo;
    private final FileStorage fileStorage;

    @Override
    public void createProductVariant(Long productId, List<ProductVariantRequest> variantRequest) {
        ProductEntity productEntity = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        // luu bang variant attribute value
        Set<Long> attributeIds = variantRequest.stream()
                .flatMap(v -> v.getVariant_attibute_value().stream())
                .map(Variant_Attibute_Value_Request::getAttributeId)
                .collect(Collectors.toSet());
        Map<Long, VariantAtrributeEntity> attributeMap = variantAttributeRepo.findAllById(attributeIds)
                .stream().collect(Collectors.toMap(VariantAtrributeEntity::getId, a -> a));
        List<ProductVariantEntity> variantEntities = new ArrayList<>();
        List<ProductVariantAttributeValueEntity> listVariantAttributeValue = new ArrayList<>();
        for (ProductVariantRequest req : variantRequest) {
            // map variant
            ProductVariantEntity variant = variantMapper.toEntity(req);
            variant.setProduct(productEntity);
            variantEntities.add(variant);
            if(!req.getVariant_attibute_value().isEmpty()){
                for (Variant_Attibute_Value_Request v : req.getVariant_attibute_value()) {
                    ProductVariantAttributeValueEntity attributeValueEntity = new ProductVariantAttributeValueEntity();
                    attributeValueEntity.setVariant(variant);
                    attributeValueEntity.setAttribute(attributeMap.get(v.getAttributeId()));
                    attributeValueEntity.setValue(v.getValue());
                    attributeValueEntity.setImageVariant(v.getImageVariant());
                    listVariantAttributeValue.add(attributeValueEntity);
                }
            }

        }
        productVariantRepo.saveAll(variantEntities);
        if(!listVariantAttributeValue.isEmpty()){
            varianAttribueValueRepo.saveAll(listVariantAttributeValue);
        }

    }

    public String uploadImageProductVariant(MultipartFile productVariantImage) {
        String filename =fileStorage.saveFile(productVariantImage, Folder.ProductVariantImage.name());
        return filename;
    }
    @Override
    public void updateProductVariant(Long id, ProductVariantRequest productVariantRequest) {

    }

    @Override
    public void deleteProductVariant(Long id) {

    }

    @Override
    public ProductVariantEntity getProductVariant(Long idProduct) {
        return null;
    }
}
