package TMDT.example.TMDT.Products.Service;

import TMDT.example.TMDT.Products.Payload.Reponse.CategoryReponse;
import TMDT.example.TMDT.Products.Payload.Request.CateGoryRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CateGoryService {
    void createCateGory(CateGoryRequest  cateGoryRequest, MultipartFile file);
    void updateCateGory(Long id,CateGoryRequest cateGoryRequest,MultipartFile file);
    void deleteCateGory(Long id);
    List<CategoryReponse> getAllCateGory();
}
