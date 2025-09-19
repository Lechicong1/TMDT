package TMDT.example.TMDT.Shop.Service;

import TMDT.example.TMDT.Shop.Payload.Reponse.RegisterSellerReponse;
import TMDT.example.TMDT.Shop.Payload.Request.RegisterSellerRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SellerRegisterService {
    void RegisterSeller(RegisterSellerRequest request, MultipartFile file);
    void confirmSeller(Long id,int option);
    List<RegisterSellerReponse> getAllPendingSellerRegister(); // lay ra cac shop dang cho duoc xet duyet
    List<RegisterSellerReponse> getAllApprovedSellerRegister();
}
