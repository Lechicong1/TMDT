package TMDT.example.TMDT.Shop.Service;

import TMDT.example.TMDT.Shop.Payload.Request.RegisterSellerRequest;
import org.springframework.web.multipart.MultipartFile;

public interface SellerRegisterService {
    void RegisterSeller(RegisterSellerRequest request, MultipartFile file);
    void confirmSeller(Long id,int option);
}
