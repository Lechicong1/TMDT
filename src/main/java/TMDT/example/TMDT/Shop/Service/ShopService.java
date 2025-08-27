package TMDT.example.TMDT.Shop.Service;

import TMDT.example.TMDT.Shop.Payload.Reponse.ShopReponse;
import TMDT.example.TMDT.Shop.Payload.Request.ShopRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShopService {
    void updateShop(Long idShop, ShopRequest request, MultipartFile logo);
    void deleteShop(Long idShop);
    List<ShopReponse> getAllShops();
    ShopReponse myShop();
}
