package TMDT.example.TMDT.Delivery.ServiceImp;

import TMDT.example.TMDT.Delivery.DTO.Mapper.DeliveryCarrierMapper;
import TMDT.example.TMDT.Delivery.DTO.Reponse.DeliveryReponse;
import TMDT.example.TMDT.Delivery.DTO.Reponse.ShopDeliveryReponse;
import TMDT.example.TMDT.Delivery.DTO.Request.ShopDeliveryRequest;
import TMDT.example.TMDT.Delivery.Entity.DeliveryServiceEntity;
import TMDT.example.TMDT.Delivery.Entity.ShopDeliveryServiceEntity;
import TMDT.example.TMDT.Delivery.Repository.DeliveryServiceRepo;
import TMDT.example.TMDT.Delivery.Repository.ShopDeliveryRepo;
import TMDT.example.TMDT.Delivery.Service.DeliveryService;
import TMDT.example.TMDT.Delivery.Service.ShopDeliveryService;
import TMDT.example.TMDT.Enums.Folder;
import TMDT.example.TMDT.Exception.ResourceNotFoundException;
import TMDT.example.TMDT.Shop.Entity.ShopEntity;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import TMDT.example.TMDT.Users.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopDeliveryServiceImp implements ShopDeliveryService {
    private final UserRepo userRepo;
    private final DeliveryServiceRepo deliveryServiceRepo;
    private final ShopDeliveryRepo shopDeliveryRepo;
    private final DeliveryCarrierMapper mapper;
    @Value("${app.base-url}")
    protected String baseUrl;
    @Override
    public void updateShopDeliveryService(Set<Long> idServiceDeliveryNew) {
        var context = SecurityContextHolder.getContext();
        Long userId = Long.parseLong(
                ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject()
        );
        UserEntity usersCur = userRepo.findByIdFetchShop(userId);
        if (usersCur == null) throw new ResourceNotFoundException("User not found");
        ShopEntity shopEntity = usersCur.getShop();
        if (shopEntity == null) throw new ResourceNotFoundException("Shop not found");
        // vd dtb {1,2,3} , fe [ 1,2]
        Set<Long> idShopDeliveryServiceCur = shopDeliveryRepo.findIdDeliveryServiceByShopId(shopEntity.getId());
        // delete nhung id co trong list cu nhung k co trong list moi
        // duyet tung phan tu trong mang cu , kiem tra xem id trong mang cu co ton tai trong mang moi k , neu k co tra ve true
        List<Long> toDelete = idShopDeliveryServiceCur.stream().filter(id -> !idServiceDeliveryNew.contains(id)).collect(Collectors.toList());
        if (!toDelete.isEmpty()) {
            shopDeliveryRepo.deleteByShopAndDeliveryService(shopEntity.getId(), toDelete);
        }
        // insert nhung cai con thieu
        List<Long> toAdd = idServiceDeliveryNew.stream().filter(id -> !idShopDeliveryServiceCur.contains(id)).collect(Collectors.toList());
        if(toAdd.isEmpty()) return;
        List<DeliveryServiceEntity> listDeliveryServiceEntity = deliveryServiceRepo.findAllByIdIn(toAdd);

        List<ShopDeliveryServiceEntity> shopDeliveryServiceEntity = listDeliveryServiceEntity.stream()
                .map(d -> {
                    ShopDeliveryServiceEntity s = new ShopDeliveryServiceEntity();
                    s.setDeliveryService(d);
                    s.setShop(shopEntity);
                    s.setEnabled(true);
                    return s;
                }).collect(Collectors.toList());
        shopDeliveryRepo.saveAll(shopDeliveryServiceEntity);
    }


    @Override
    public List<ShopDeliveryReponse> getAllDeliveryServiceByShop() {
        var context = SecurityContextHolder.getContext();
        Long userId = Long.parseLong(
                ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject()
        );
        UserEntity usersCur = userRepo.findByIdFetchShop(userId);
        if (usersCur == null) throw new ResourceNotFoundException("User not found");
        ShopEntity shopEntity = usersCur.getShop();
        if (shopEntity == null) throw new ResourceNotFoundException("Shop not found");

        List<DeliveryServiceEntity> allDeliveryServices = deliveryServiceRepo.findAll();
        Set<Long> enabledIdDeliveryService = shopDeliveryRepo.findIdDeliveryServiceByShopId(shopEntity.getId());

        List<ShopDeliveryReponse> result = allDeliveryServices.stream()
                .map(service -> new ShopDeliveryReponse(
                        service.getId(),
                        service.getName(),
                        enabledIdDeliveryService.contains(service.getId()),
                        baseUrl + "/Upload/" + Folder.logoDeliveryCarrier.name() + "/" + service.getLogoUrl()
                ))
                .toList();

        return result;
    }

}   
