package TMDT.example.TMDT.Delivery.Service;

import TMDT.example.TMDT.Delivery.DTO.Reponse.DeliveryReponse;
import TMDT.example.TMDT.Delivery.DTO.Reponse.ShopDeliveryReponse;
import TMDT.example.TMDT.Delivery.DTO.Request.ShopDeliveryRequest;

import java.util.List;
import java.util.Set;

public interface ShopDeliveryService {
    void updateShopDeliveryService(Set<Long> idDerliveryService);
    List<ShopDeliveryReponse> getAllDeliveryServiceByShop();   // liet ke cac don vi van chuyen cua 1 shop

}
