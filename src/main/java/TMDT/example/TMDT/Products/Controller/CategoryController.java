package TMDT.example.TMDT.Products.Controller;

import TMDT.example.TMDT.Products.Payload.Reponse.CategoryReponse;
import TMDT.example.TMDT.Products.Payload.Request.CateGoryRequest;
import TMDT.example.TMDT.Products.ServiceImp.CateGoryServiceImp;
import TMDT.example.TMDT.Respone.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/category")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public class CategoryController {
        private final CateGoryServiceImp  cateGoryServiceImp;
        @PostMapping
        ResponseEntity<?> createCategory(@RequestPart String  req, @RequestPart MultipartFile file){
            ResponseData responseData = new ResponseData();
            HttpStatus status = HttpStatus.CREATED;
            ObjectMapper mapper = new ObjectMapper();
            CateGoryRequest cateGoryRequest  = null;
            try {
                cateGoryRequest = mapper.readValue(req, CateGoryRequest.class);
            } catch (Exception e) {

                return ResponseEntity.badRequest().body("Invalid Category JSON");
            }
            cateGoryServiceImp.createCateGory(cateGoryRequest,file);
            responseData.setMessage("Category has been successfully created");
            responseData.setSuccess(true);
            return ResponseEntity.status(status).body(responseData);

    }
    @PutMapping("/{id}")
    ResponseEntity<?> updateCategory(  @PathVariable Long id ,@RequestPart String req,MultipartFile file){
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        ObjectMapper mapper = new ObjectMapper();
        CateGoryRequest cateGoryRequest  = null;
        try {
            cateGoryRequest = mapper.readValue(req, CateGoryRequest.class);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Invalid Category JSON");
        }
        cateGoryServiceImp.updateCateGory(id, cateGoryRequest,file);
        responseData.setMessage("Category has been successfully updated");
        responseData.setSuccess(true);
        return ResponseEntity.status(status).body(responseData);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCategory(@PathVariable Long id){
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        cateGoryServiceImp.deleteCateGory(id);
        responseData.setMessage("Category has been successfully deleted");
        responseData.setSuccess(true);
        return ResponseEntity.status(status).body(responseData);
    }
    @GetMapping
    ResponseEntity<?> getAllCategory(){
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        List<CategoryReponse> list = cateGoryServiceImp.getAllCateGory();
        responseData.setMessage("Category has been successfully retrieved");
        responseData.setSuccess(true);
        responseData.setData(list);
        return ResponseEntity.status(status).body(responseData);
    }
}
