package TMDT.example.TMDT.Products.ServiceImp;

import TMDT.example.TMDT.Enums.Folder;
import TMDT.example.TMDT.Exception.ResourceNotFoundException;
import TMDT.example.TMDT.Products.Entity.*;
import TMDT.example.TMDT.Products.Payload.Mapper.ProductMapper;
import TMDT.example.TMDT.Products.Payload.Reponse.ProductDetailDTO;
import TMDT.example.TMDT.Products.Payload.Reponse.ProductReponse;
import TMDT.example.TMDT.Products.Payload.Request.ProductRequest;
import TMDT.example.TMDT.Products.Payload.Request.ProductVariantRequest;
import TMDT.example.TMDT.Products.Payload.Request.Variant_Attibute_Value_Request;
import TMDT.example.TMDT.Products.Payload.projection.ProductDetailView;
import TMDT.example.TMDT.Products.Repo.*;
import TMDT.example.TMDT.Products.Service.ProductService;
import TMDT.example.TMDT.Products.Specification.ProductSpecification;
import TMDT.example.TMDT.Respone.PageReponse;
import TMDT.example.TMDT.Shop.Entity.ShopEntity;
import TMDT.example.TMDT.Shop.Repository.ShopRepo;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import TMDT.example.TMDT.Users.Repository.UserRepo;
import TMDT.example.TMDT.Utils.FileStorage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor

public class ProductServiceImp implements ProductService {
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final CateGoryRepo cateGoryRepo;
    private final ProductMapper productMapper;
    private final FileStorage fileStorage;

    @Override
    public Long CreateProduct(ProductRequest productRequest, MultipartFile productImage) {
        var context = SecurityContextHolder.getContext();
        Long userId = Long.parseLong(
                ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject()
        );
        UserEntity usersCur = userRepo.findByIdFetchShop(userId);
        if (usersCur == null) {
            throw new ResourceNotFoundException("User not found");
        }
        ShopEntity shop = usersCur.getShop();
        if (shop == null) {
            throw new ResourceNotFoundException("Shop not found");
        }
        CategoryEntity category = cateGoryRepo.findById(productRequest.getIdCategory()).orElseThrow(() -> new RuntimeException("Category not found"));
        ProductEntity productEntity = productMapper.toEntity(productRequest);
        String fileName = fileStorage.saveFile(productImage, Folder.ProductImage.name());
        productEntity.setProductImage(fileName);
        productEntity.setShop(shop);
        productEntity.setCategory(category);

        productEntity.setCreatedAt(LocalDateTime.now());
        productRepo.save(productEntity);

        return productEntity.getId();
    }


    @Override
    public Long UpdateProduct(Long id, ProductRequest productRequest, MultipartFile productImage) {
        return 1L;
    }

    @Override
    public void DeleteProduct(Long id) {

    }

    private Sort getSortFromOption(String sortOption) {
        if (sortOption == null) {
            sortOption = "price_asc"; // hoặc giá trị mặc định bạn muốn
        }
        return switch (sortOption) {
            case "price_desc" -> Sort.by("price").descending();
            default -> Sort.by("price").ascending();
        };
    }


    @Override
    public PageReponse<ProductReponse> getAllProducts(String shopAddress,
                                                      Integer categoryId,
                                                      String nameProduct,
                                                      Integer page, Integer size,
                                                      String sortOption) {

        Sort sort = getSortFromOption(sortOption);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProductEntity> productEntities = productRepo
                .findAll(ProductSpecification.search(shopAddress, categoryId,nameProduct), pageable);
        Page<ProductReponse> productReponses = productEntities.map(p -> productMapper.toDTO(p));
        return new PageReponse<>(productReponses);
    }

    @Override
    public List<ProductReponse> getAllProductsByShop() {
        var context = SecurityContextHolder.getContext();
        Long userId = Long.parseLong(
                ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject()
        );
        UserEntity usersCur = userRepo.findByIdFetchShop(userId);
        if (usersCur == null) throw new ResourceNotFoundException("User not found");
        ShopEntity shop = usersCur.getShop();
        if (shop == null) throw new ResourceNotFoundException("Shop not found");
        List<ProductEntity> listProduct = productRepo.findAllProductByShop(shop);
        return productMapper.toDTOList(listProduct);
    }

    @Override
    public List<ProductDetailDTO> getDetailProduct(Long id) {
        List<ProductDetailView> productDetailViews = productRepo.getProductDetail(id);

        return productMapper.toDTODetail(productDetailViews);
    }
}
