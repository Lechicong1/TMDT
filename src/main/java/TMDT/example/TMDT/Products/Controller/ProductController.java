package TMDT.example.TMDT.Products.Controller;

import TMDT.example.TMDT.Products.Payload.Reponse.ProductDetailDTO;
import TMDT.example.TMDT.Products.Payload.Reponse.ProductReponse;
import TMDT.example.TMDT.Products.Payload.Request.ProductRequest;
import TMDT.example.TMDT.Products.Service.ProductService;
import TMDT.example.TMDT.Respone.PageReponse;
import TMDT.example.TMDT.Respone.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")

public class ProductController {
    private final ProductService productService;
    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping
    ResponseEntity<?> createProduct(@RequestPart String req, @RequestPart MultipartFile file) {
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.CREATED;
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("request " + req);
        ProductRequest productRequest = null;
        try {
            productRequest = mapper.readValue(req, ProductRequest.class);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Invalid Category JSON");
        }
        Long idProduct = productService.CreateProduct(productRequest, file);
        responseData.setData(idProduct);
        responseData.setMessage("Product has been successfully created");
        responseData.setSuccess(true);
        return ResponseEntity.status(status).body(responseData);

    }
    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping("/{id}")
    ResponseEntity<?> updateProduct(@PathVariable Long id,
                                    @RequestPart String req,
                                    MultipartFile file) {
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        ObjectMapper mapper = new ObjectMapper();
        ProductRequest productRequest = null;
        try {
            productRequest = mapper.readValue(req, ProductRequest.class);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Invalid Category JSON");
        }
        Long idProduct = productService.CreateProduct(productRequest, file);
        responseData.setData(idProduct);
        responseData.setMessage("Product has been successfully updated");
        responseData.setSuccess(true);
        return ResponseEntity.status(status).body(responseData);
    }
    @PreAuthorize("hasAuthority('SELLER')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        productService.DeleteProduct(id);
        responseData.setMessage("Produt has been successfully deleted");
        responseData.setSuccess(true);
        return ResponseEntity.status(status).body(responseData);
    }

    @GetMapping
    ResponseEntity<?> getAllproducts(@RequestParam(required = false) String shopAddress,
                                     @RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false) String name,
                                     @RequestParam Integer page,
                                     @RequestParam Integer size,
                                     @RequestParam(required = false) String sortBy) {
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        PageReponse<ProductReponse> list = productService.getAllProducts(shopAddress, categoryId,name, page-1, size, sortBy);
        responseData.setMessage("Product has been successfully retrieved");
        responseData.setSuccess(true);
        responseData.setData(list);
        return ResponseEntity.status(status).body(responseData);
    }
    @GetMapping(value = "/details/{id}")
    ResponseEntity<?> getProductDetail(@PathVariable Long id) {
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        List<ProductDetailDTO> list = productService.getDetailProduct(id);
        responseData.setMessage("Product detail has been successfully retrieved");
        responseData.setSuccess(true);
        responseData.setData(list);
        return ResponseEntity.status(status).body(responseData);
    }

    @GetMapping("/myShop")
    ResponseEntity<?> getAllproductByShop() {
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        List<ProductReponse> list = productService.getAllProductsByShop();
        responseData.setMessage("Product has been successfully retrieved");
        responseData.setSuccess(true);
        responseData.setData(list);
        return ResponseEntity.status(status).body(responseData);
    }


}
