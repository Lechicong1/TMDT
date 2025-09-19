package TMDT.example.TMDT.Delivery.ServiceImp;

import TMDT.example.TMDT.Delivery.DTO.Mapper.DeliveryCarrierMapper;
import TMDT.example.TMDT.Delivery.DTO.Reponse.DeliveryReponse;
import TMDT.example.TMDT.Delivery.DTO.Request.CreateDeliveryCarrierRequest;
import TMDT.example.TMDT.Delivery.Entity.DeliveryServiceEntity;
import TMDT.example.TMDT.Delivery.Repository.DeliveryServiceRepo;
import TMDT.example.TMDT.Delivery.Service.DeliveryService;
import TMDT.example.TMDT.Enums.Folder;
import TMDT.example.TMDT.Exception.ResourceNotFoundException;
import TMDT.example.TMDT.Utils.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImp implements DeliveryService {
    private final DeliveryServiceRepo deliveryServiceRepo;
    private final FileStorage fileStorage;
    private final DeliveryCarrierMapper deliveryCarrierMapper;

    @Override
    public void CreateDeliveryCarrier(CreateDeliveryCarrierRequest request, MultipartFile logo) {
        DeliveryServiceEntity deliveryCarrier = new DeliveryServiceEntity();
        deliveryCarrier.setName(request.getName());
        deliveryCarrier.setDescription(request.getDescription());
        deliveryCarrier.setCode(request.getCode());
        deliveryCarrier.setToken(request.getToken());
        deliveryCarrier.setApiUrlCalculateFee(request.getApiUrlCalculateFee());
        deliveryCarrier.setService_type_id(request.getService_type_id());
        deliveryCarrier.setApiUrlCreateOrder(request.getApiUrlCreateOrder());
        deliveryCarrier.setApiUrlTracking(request.getApiUrlTracking());
        String fileName = fileStorage.saveFile(logo, Folder.logoDeliveryCarrier.name());
        deliveryCarrier.setLogoUrl(fileName);
        deliveryCarrier.setActive(true);

        deliveryServiceRepo.save(deliveryCarrier);
    }

    @Override
    public void UpdateDeliveryCarrier(Long id, CreateDeliveryCarrierRequest request, MultipartFile logo) {
        DeliveryServiceEntity deliveryCarrier = deliveryServiceRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carrier delivery not found"));
        if (request.getName() != null) deliveryCarrier.setName(request.getName());
        if (request.getDescription() != null) deliveryCarrier.setDescription(request.getDescription());
        if (request.getCode() != null) deliveryCarrier.setCode(request.getCode());
        if (request.getToken() != null) deliveryCarrier.setToken(request.getToken());
        if (request.getApiUrlCalculateFee() != null)
            deliveryCarrier.setApiUrlCalculateFee(request.getApiUrlCalculateFee());
        if (request.getApiUrlCreateOrder() != null)
            deliveryCarrier.setApiUrlCreateOrder(request.getApiUrlCreateOrder());
        if (request.getApiUrlTracking() != null) deliveryCarrier.setApiUrlTracking(request.getApiUrlTracking());

        deliveryCarrier.setService_type_id(request.getService_type_id());
        String img = deliveryCarrier.getLogoUrl();
        if (logo != null && !logo.isEmpty()) {
            String fileName = fileStorage.saveFile(logo, Folder.logoDeliveryCarrier.name());
            img = fileName; //ten file anh
        }
        deliveryCarrier.setLogoUrl(img);
        deliveryServiceRepo.save(deliveryCarrier);
    }

    @Override
    public void DeleteDeliveryCarrier(Long id) {
        if (!deliveryServiceRepo.existsById(id)) {
            throw new ResourceNotFoundException("Carrier delivery not found");
        }
        deliveryServiceRepo.deleteById(id);
    }

    @Override
    public List<DeliveryReponse> FindAllDeliveryCarriers() {
        List<DeliveryServiceEntity> listCarrierDelivery = deliveryServiceRepo.findAll();

        return deliveryCarrierMapper.toDTOList(listCarrierDelivery);

    }
}
