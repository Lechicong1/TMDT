package TMDT.example.TMDT.Products.Controller;

import TMDT.example.TMDT.Products.Payload.Reponse.VariantAttributeReponse;
import TMDT.example.TMDT.Products.Payload.Request.VariantAttributeRequest;
import TMDT.example.TMDT.Products.ServiceImp.VariantAttributeServiceImp;
import TMDT.example.TMDT.Respone.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
    @RequestMapping("/VarriantAttribute")
//@PreAuthorize("hasAuthority('ADMIN')")
public class VariantAttributeController {
    private final VariantAttributeServiceImp variantAttributeServiceImp;

    @PostMapping
    ResponseEntity<?> createVariantAttribute(@RequestBody VariantAttributeRequest req){
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.CREATED;
        variantAttributeServiceImp.createVariantAttibute(req);
        responseData.setMessage("Variant Attribute has been successfully created");
        responseData.setSuccess(true);
        return ResponseEntity.status(status).body(responseData);

    }
    @PutMapping("/{id}")
    ResponseEntity<?> updateVariantAttribute(  @PathVariable Long id ,@RequestBody VariantAttributeRequest req){
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        variantAttributeServiceImp.updateVariantAttibute(id, req);
        responseData.setMessage("Variant Attribute has been successfully updated");
        responseData.setSuccess(true);
        return ResponseEntity.status(status).body(responseData);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteVariantAttribute(@PathVariable Long id){
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        variantAttributeServiceImp.deleteVariantAttibute(id);
        responseData.setMessage("Variant Attribute has been successfully deleted");
        responseData.setSuccess(true);
        return ResponseEntity.status(status).body(responseData);
    }
    @GetMapping
    ResponseEntity<?> getAllVariantAttribute(){
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.OK;
        List<VariantAttributeReponse> list = variantAttributeServiceImp.getAllVariantAttribute();
        responseData.setMessage("Variant Attribute has been successfully retrieved");
        responseData.setSuccess(true);
        responseData.setData(list);
        return ResponseEntity.status(status).body(responseData);
    }
}
