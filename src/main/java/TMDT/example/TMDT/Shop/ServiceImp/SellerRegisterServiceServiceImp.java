package TMDT.example.TMDT.Shop.ServiceImp;

import TMDT.example.TMDT.Enums.RegisterStatus;
import TMDT.example.TMDT.Enums.ShopStatus;
import TMDT.example.TMDT.Exception.DuplicateResourceException;
import TMDT.example.TMDT.Exception.ResourceNotFoundException;
import TMDT.example.TMDT.Shop.Entity.SellerRegisterEntity;
import TMDT.example.TMDT.Shop.Entity.ShopEntity;
import TMDT.example.TMDT.Shop.Payload.Request.RegisterSellerRequest;
import TMDT.example.TMDT.Shop.Repository.SellerRegisterRepo;
import TMDT.example.TMDT.Shop.Repository.ShopRepo;
import TMDT.example.TMDT.Shop.Service.SellerRegisterService;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import TMDT.example.TMDT.Users.Repository.UserRepo;
import TMDT.example.TMDT.Utils.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SellerRegisterServiceServiceImp implements SellerRegisterService {
    private final SellerRegisterRepo repo;
    private final FileStorage fileStorage;
    private final UserRepo userRepo;
    private final ShopRepo shopRepo;

    @Override
    public void RegisterSeller(RegisterSellerRequest request, MultipartFile multipartFile) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        UserEntity usersCur = userRepo.findByUsername(username);
        if (usersCur == null) {
            throw new ResourceNotFoundException("User not found");
        }
        if (repo.existsByUserId(usersCur.getId()))
            throw new DuplicateResourceException("You have registered as a seller, please wait for approval.");
        SellerRegisterEntity sellerRegisterEntity = new SellerRegisterEntity();
        sellerRegisterEntity.setStatus(RegisterStatus.PENDING);
        sellerRegisterEntity.setShopName(request.getShopName());
        sellerRegisterEntity.setSubmittedAt(LocalDateTime.now());
        sellerRegisterEntity.setUser(usersCur);
        String filename = fileStorage.saveFile(multipartFile, "SellerRegister");
        sellerRegisterEntity.setIdentityDocumentUrl(filename);
        repo.save(sellerRegisterEntity);

    }

    @Override
    public void confirmSeller(Long idRegister, int option) {
        SellerRegisterEntity register = repo.findByIdCustom(idRegister);
        if (register == null) {
            throw new ResourceNotFoundException("No user has registered to become a seller yet.");
        }
        if (register.getStatus() != RegisterStatus.PENDING)
            throw new DuplicateResourceException("This request has already been reviewed.");
        if (option == 0) register.setStatus(RegisterStatus.REJECTED);
        if (option == 1) {
            register.setStatus(RegisterStatus.APPROVED);
            // them vao bang shop
            ShopEntity shopEntity = ShopEntity.builder()
                    .seller(register.getUser())
                    .shopName(register.getShopName())
                    .status(ShopStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build();
            shopRepo.save(shopEntity);

        }
        register.setReviewedAt(LocalDateTime.now());
        repo.save(register);
    }
}
