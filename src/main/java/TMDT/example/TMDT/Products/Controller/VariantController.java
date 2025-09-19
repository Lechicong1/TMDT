package TMDT.example.TMDT.Products.Controller;

import TMDT.example.TMDT.Products.Payload.Request.ProductVariantRequest;
import TMDT.example.TMDT.Products.ServiceImp.ProductVariantServiceImp;
import TMDT.example.TMDT.Respone.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productVariant")
@PreAuthorize("hasAuthority('SELLER')")
public class VariantController {
    private final ProductVariantServiceImp productVariantServiceImp;

    @PostMapping("/image")
    ResponseEntity<?> uploadVariantImage(@RequestPart MultipartFile file) {
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.CREATED;
        String filename = productVariantServiceImp.uploadImageProductVariant(file);
        responseData.setData(filename);
        responseData.setMessage("product variant image  has been successfully created");
        responseData.setSuccess(true);
        return ResponseEntity.status(status).body(responseData);
    }

    @PostMapping()
    ResponseEntity<?> createVariant(@RequestParam Long productId, @RequestBody List<ProductVariantRequest> productVariantRequest) {
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.CREATED;
        productVariantServiceImp.createProductVariant(productId, productVariantRequest);
        responseData.setMessage("product variant   has been successfully created");
        responseData.setSuccess(true);
        return ResponseEntity.status(status).body(responseData);
    }

}
