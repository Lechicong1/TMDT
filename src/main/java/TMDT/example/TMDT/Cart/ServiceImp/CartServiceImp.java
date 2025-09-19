package TMDT.example.TMDT.Cart.ServiceImp;

import TMDT.example.TMDT.Cart.DTO.CartRequest;
import TMDT.example.TMDT.Cart.Entity.CartEntity;
import TMDT.example.TMDT.Cart.Repo.CartRepo;
import TMDT.example.TMDT.Cart.Service.CartService;
import TMDT.example.TMDT.Exception.BusinessLogicException;
import TMDT.example.TMDT.Exception.ResourceNotFoundException;
import TMDT.example.TMDT.Products.Entity.ProductVariantEntity;
import TMDT.example.TMDT.Products.Repo.ProductVariantRepo;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import TMDT.example.TMDT.Users.Repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Transactional
@RequiredArgsConstructor
@Service
public class CartServiceImp implements CartService {
    private final CartRepo cartRepo;
    private final ProductVariantRepo productVariantRepo;
    private final UserRepo userRepo;

    @Override
    public void addItemToCart(CartRequest req) {
        ProductVariantEntity variantEntity = productVariantRepo.findById(req.getVariantId()).orElseThrow(() -> new ResourceNotFoundException("product variant not found"));
        var context = SecurityContextHolder.getContext();
        Long userId = Long.parseLong(
                ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject()
        );
        UserEntity usersCur = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        if(req.getQuantityItem()>variantEntity.getQuantity()){
            throw new BusinessLogicException("Số lượng không được vượt quá số lượng trong kho");
        }
        CartEntity cartEntity = CartEntity.builder()
                .variant(variantEntity)
                .buyer(usersCur)
                .quantity(req.getQuantityItem())
                .addedAt(LocalDateTime.now())
                .build();
        cartRepo.save(cartEntity);
    }

    @Override
    public void removeItemFromCart(Long variantId) {
        if(!cartRepo.existsById(variantId)){
            throw new ResourceNotFoundException("cart not found");
        }
        cartRepo.deleteById(variantId);
    }

    @Override
    public void getAllItemsFromCart() {

    }
}
