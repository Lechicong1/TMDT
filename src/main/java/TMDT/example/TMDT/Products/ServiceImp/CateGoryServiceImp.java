package TMDT.example.TMDT.Products.ServiceImp;

import TMDT.example.TMDT.Enums.Folder;
import TMDT.example.TMDT.Exception.ResourceNotFoundException;
import TMDT.example.TMDT.Products.Entity.CategoryEntity;
import TMDT.example.TMDT.Products.Payload.Mapper.CateGoryMapper;
import TMDT.example.TMDT.Products.Payload.Reponse.CategoryReponse;
import TMDT.example.TMDT.Products.Payload.Request.CateGoryRequest;
import TMDT.example.TMDT.Products.Repo.CateGoryRepo;
import TMDT.example.TMDT.Products.Service.CateGoryService;
import TMDT.example.TMDT.Utils.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CateGoryServiceImp implements CateGoryService {
    private final CateGoryRepo cateGoryRepo;
    private final CateGoryMapper mapper;
    private final FileStorage  fileStorage;
    @Override
    public void createCateGory(CateGoryRequest cateGoryRequest, MultipartFile img) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(cateGoryRequest.getName());
        String filename = fileStorage.saveFile(img, Folder.CategoryImage.name());
        entity.setImageCategory(filename);
        cateGoryRepo.save(entity);
    }

    @Override
    public void updateCateGory(Long id, CateGoryRequest req,MultipartFile img) {
        CategoryEntity entity = cateGoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        if(req.getName()!=null) entity.setName(req.getName());
        String filename = entity.getImageCategory();
        if(img!=null && !img.isEmpty()) {
            String file = fileStorage.saveFile(img,Folder.CategoryImage.name());
            filename = file;
        }
         if(filename!=null) entity.setImageCategory(filename);
        cateGoryRepo.save(entity);
    }

    @Override
    public void deleteCateGory(Long id) {
        if(!cateGoryRepo.existsById(id)) throw new ResourceNotFoundException("Category not found");
        cateGoryRepo.deleteById(id);
    }

    @Override
    public List<CategoryReponse> getAllCateGory() {
        List<CategoryEntity>  list = cateGoryRepo.findAll();
        return mapper.toDTO(list);
    }
}
