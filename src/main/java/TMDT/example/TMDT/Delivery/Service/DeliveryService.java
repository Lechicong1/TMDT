package TMDT.example.TMDT.Delivery.Service;

import TMDT.example.TMDT.Delivery.DTO.Reponse.DeliveryReponse;
import TMDT.example.TMDT.Delivery.DTO.Request.CreateDeliveryCarrierRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DeliveryService {
    void CreateDeliveryCarrier(CreateDeliveryCarrierRequest request, MultipartFile file);
    void UpdateDeliveryCarrier(Long id , CreateDeliveryCarrierRequest request,MultipartFile file);
    void DeleteDeliveryCarrier(Long id);
    List<DeliveryReponse> FindAllDeliveryCarriers();

}
