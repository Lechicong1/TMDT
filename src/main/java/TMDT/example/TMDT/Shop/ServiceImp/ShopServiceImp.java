package TMDT.example.TMDT.Shop.ServiceImp;

import TMDT.example.TMDT.Enums.Folder;
import TMDT.example.TMDT.Exception.ResourceNotFoundException;
import TMDT.example.TMDT.Shop.Entity.ShopEntity;
import TMDT.example.TMDT.Shop.Payload.Mapper.ShopMapper;
import TMDT.example.TMDT.Shop.Payload.Reponse.ShopReponse;
import TMDT.example.TMDT.Shop.Payload.Request.ShopRequest;
import TMDT.example.TMDT.Shop.Repository.ShopRepo;
import TMDT.example.TMDT.Shop.Service.ShopService;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import TMDT.example.TMDT.Users.Repository.UserRepo;
import TMDT.example.TMDT.Utils.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopServiceImp implements ShopService {
    private final ShopRepo repo;
    private final FileStorage fileStorage;
    private final ShopMapper shopMapper;
    private final UserRepo userRepo;
    @Override
    public void updateShop( ShopRequest request, MultipartFile logo) {
        var context = SecurityContextHolder.getContext();
        Long userId = Long.parseLong(
                ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject()
        );
        UserEntity usersCur = userRepo.findByIdFetchShop(userId);
        if(usersCur==null) throw new ResourceNotFoundException("User not found");
        ShopEntity shopEntity = usersCur.getShop();
        if(shopEntity ==null) throw new ResourceNotFoundException("Shop not found");

        if(request.getShopAddress()!=null) shopEntity.setShopAddress(request.getShopAddress());
        if(request.getShopName()!=null) shopEntity.setShopName(request.getShopName());
        if(request.getShopDescription()!=null) shopEntity.setShopDescription(request.getShopDescription());
        String img = shopEntity.getShopLogoUrl();
        if (logo != null && !logo.isEmpty()) {
            String fileName = fileStorage.saveFile(logo, Folder.LogoShop.name());
            img = fileName; //ten file anh
        }
        if (img != null) shopEntity.setShopLogoUrl(img);
        shopEntity.setUpdatedAt(LocalDateTime.now());
        repo.save(shopEntity);
    }

    @Override
    public void deleteShop(Long idShop) {
        if(!repo.existsById(idShop)) throw new ResourceNotFoundException("Shop not found");
        repo.deleteById(idShop);

    }

    @Override
    public List<ShopReponse> getAllShops() {
        List<ShopEntity> entities = repo.findAll();
        return shopMapper.toDTOList(entities);
    }

    @Override
    public ShopReponse myShop() {
        var context = SecurityContextHolder.getContext();
        Long userId = Long.parseLong(
                ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject()
        );
        UserEntity usersCur = userRepo.findByIdFetchShop(userId);
        if(usersCur==null) throw new ResourceNotFoundException("User not found");
        ShopEntity shopEntity = usersCur.getShop();
        if(shopEntity ==null) throw new ResourceNotFoundException("Shop not found");
        return shopMapper.toDTO(shopEntity);
    }
}
