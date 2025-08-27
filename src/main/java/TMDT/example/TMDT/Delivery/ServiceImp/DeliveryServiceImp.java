package TMDT.example.TMDT.Delivery.ServiceImp;

import TMDT.example.TMDT.Delivery.DTO.Mapper.DeliveryCarrierMapper;
import TMDT.example.TMDT.Delivery.DTO.Mapper.DeliveryCarrierMapperImpl;
import TMDT.example.TMDT.Delivery.DTO.Reponse.DeliveryReponse;
import TMDT.example.TMDT.Delivery.DTO.Request.CreateDeliveryCarrierRequest;
import TMDT.example.TMDT.Delivery.Entity.DeliveryServiceEntity;
import TMDT.example.TMDT.Delivery.Repository.DeliveryRepo;
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
    private final DeliveryRepo deliveryRepo;
    private final FileStorage fileStorage;
    private final DeliveryCarrierMapper deliveryCarrierMapper;

    @Override
    public void CreateDeliveryCarrier(CreateDeliveryCarrierRequest request, MultipartFile logo) {
        DeliveryServiceEntity deliveryCarrier = new DeliveryServiceEntity();
        deliveryCarrier.setName(request.getName());
        deliveryCarrier.setDescription(request.getDescription());
        String fileName = fileStorage.saveFile(logo,Folder.logoDeliveryCarrier.name());
        deliveryCarrier.setLogoUrl(fileName);
        deliveryCarrier.setActive(true);
        deliveryRepo.save(deliveryCarrier);
    }

    @Override
    public void UpdateDeliveryCarrier(Long id, CreateDeliveryCarrierRequest request ,MultipartFile logo) {
        DeliveryServiceEntity deliveryCarrier = deliveryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Carrier delivery not found"));
        deliveryCarrier.setName(request.getName());
        deliveryCarrier.setDescription(request.getDescription());
        deliveryCarrier.setActive(request.isActive());
        String img = deliveryCarrier.getLogoUrl();
        if (logo != null && !logo.isEmpty()) {
            String fileName = fileStorage.saveFile(logo, Folder.logoDeliveryCarrier.name());
            img = fileName; //ten file anh
        }
        deliveryCarrier.setLogoUrl(img);
        deliveryRepo.save(deliveryCarrier);
    }

    @Override
    public void DeleteDeliveryCarrier(Long id) {
        if(!deliveryRepo.existsById(id)) {
            throw new ResourceNotFoundException("Carrier delivery not found");
        }
        deliveryRepo.deleteById(id);
    }

    @Override
    public List<DeliveryReponse> FindAllDeliveryCarriers() {
        List<DeliveryServiceEntity> listCarrierDelivery = deliveryRepo.findAll();
        DeliveryCarrierMapper mapper;
        return deliveryCarrierMapper.toDTOList(listCarrierDelivery);

    }
}
