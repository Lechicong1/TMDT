package TMDT.example.TMDT.Shop.ServiceImp;

import TMDT.example.TMDT.Enums.Folder;
import TMDT.example.TMDT.Enums.RegisterStatus;
import TMDT.example.TMDT.Enums.Role;
import TMDT.example.TMDT.Enums.ShopStatus;
import TMDT.example.TMDT.Exception.DuplicateResourceException;
import TMDT.example.TMDT.Exception.ResourceNotFoundException;
import TMDT.example.TMDT.Shop.Entity.SellerRegisterEntity;
import TMDT.example.TMDT.Shop.Entity.ShopEntity;
import TMDT.example.TMDT.Shop.Payload.Mapper.RegisterSellerMapper;
import TMDT.example.TMDT.Shop.Payload.Reponse.RegisterSellerReponse;
import TMDT.example.TMDT.Shop.Payload.Request.RegisterSellerRequest;
import TMDT.example.TMDT.Shop.Repository.SellerRegisterRepo;
import TMDT.example.TMDT.Shop.Repository.ShopRepo;
import TMDT.example.TMDT.Shop.Service.SellerRegisterService;
import TMDT.example.TMDT.Users.Entity.RolesEntity;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import TMDT.example.TMDT.Users.Entity.UserRolesEntity;
import TMDT.example.TMDT.Users.Repository.RoleRepo;
import TMDT.example.TMDT.Users.Repository.UserRepo;
import TMDT.example.TMDT.Users.Repository.UserRoleRepo;
import TMDT.example.TMDT.Utils.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerRegisterServiceServiceImp implements SellerRegisterService {
    private final SellerRegisterRepo SellerRegisterRepo;
    private final FileStorage fileStorage;
    private final UserRepo userRepo;
    private final ShopRepo shopRepo;
    private final RoleRepo roleRepo;
    private final UserRoleRepo userRoleRepo;
    private final RegisterSellerMapper mapper;
    @Override
    public void RegisterSeller(RegisterSellerRequest request, MultipartFile multipartFile) {
        var context = SecurityContextHolder.getContext();
        Long userId = Long.parseLong(
                ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject()
        );
        System.out.println("userId = " + userId);

        UserEntity usersCur = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (SellerRegisterRepo.existsByUserId(usersCur.getId()))
            throw new DuplicateResourceException("You have registered as a seller, please wait for approval.");
        SellerRegisterEntity sellerRegisterEntity = new SellerRegisterEntity();
        sellerRegisterEntity.setStatus(RegisterStatus.PENDING);
        sellerRegisterEntity.setShopName(request.getShopName());
        sellerRegisterEntity.setSubmittedAt(LocalDateTime.now());
        sellerRegisterEntity.setUser(usersCur);
        String filename = fileStorage.saveFile(multipartFile, Folder.SellerRegiter.name());
        sellerRegisterEntity.setIdentityDocumentUrl(filename);
        SellerRegisterRepo.save(sellerRegisterEntity);

    }

    @Override
    public void confirmSeller(Long idRegister, int option) {
        SellerRegisterEntity register = SellerRegisterRepo.findByIdCustom(idRegister);
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
            RolesEntity rolesSeller = roleRepo.findByRoleName(Role.SELLER);
            UserRolesEntity userRolesEntity = UserRolesEntity.builder()
                    .role(rolesSeller)
                    .user(register.getUser()).build();
            userRoleRepo.save(userRolesEntity);
        }
        register.setReviewedAt(LocalDateTime.now());
        SellerRegisterRepo.save(register);
    }

    @Override
    public List<RegisterSellerReponse> getAllPendingSellerRegister() {
        List<SellerRegisterEntity> list = SellerRegisterRepo.findAllByStatus(RegisterStatus.PENDING);
        return mapper.toDTOList(list);
    }

    @Override
    public List<RegisterSellerReponse> getAllApprovedSellerRegister() {
        List<SellerRegisterEntity> list = SellerRegisterRepo.findAllByStatus(RegisterStatus.APPROVED);
        return mapper.toDTOList(list);
    }
}
