package TMDT.example.TMDT.Delivery.Controller;

import TMDT.example.TMDT.Delivery.DTO.Reponse.ShopDeliveryReponse;
import TMDT.example.TMDT.Delivery.Service.ShopDeliveryService;
import TMDT.example.TMDT.Respone.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ShopDeliveryService")
@PreAuthorize("hasAuthority('SELLER')")
public class ShopDeliveryController {
    private final ShopDeliveryService shopDeliveryService;
    @GetMapping
    public ResponseEntity<?> getDeliveryServiceByShop() {
        ResponseData resp = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        List<ShopDeliveryReponse> listDeliveryReponse = shopDeliveryService.getAllDeliveryServiceByShop();
        resp.setData(listDeliveryReponse);
        resp.setSuccess(true);
        return ResponseEntity.status(status).body(resp);
    }
    @PutMapping
    public ResponseEntity<?> updateDeliveryServiceForShop(@RequestBody Set<Long> idServiceDelivery) {
        ResponseData resp = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        shopDeliveryService.updateShopDeliveryService(idServiceDelivery);
        resp.setMessage("Successfully updated shop delivery service");
        resp.setSuccess(true);
        return ResponseEntity.status(status).body(resp);
    }


}
