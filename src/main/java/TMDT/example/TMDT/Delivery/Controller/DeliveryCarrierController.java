package TMDT.example.TMDT.Delivery.Controller;

import TMDT.example.TMDT.Delivery.DTO.Reponse.DeliveryReponse;
import TMDT.example.TMDT.Delivery.DTO.Request.CreateDeliveryCarrierRequest;
import TMDT.example.TMDT.Delivery.Service.DeliveryService;
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
@RequestMapping("/DeliveryService")
public class DeliveryCarrierController {
    private final DeliveryService  deliveryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createDeliveryCarrier(@RequestPart String req,
                                                   @RequestPart("file") MultipartFile file) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.CREATED;
        ObjectMapper mapper = new ObjectMapper();
        CreateDeliveryCarrierRequest DeliveryCarrierRequest = null;
        try{
            DeliveryCarrierRequest =mapper.readValue(req,CreateDeliveryCarrierRequest.class);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Invalid teacherRequest JSON");
        }
        deliveryService.CreateDeliveryCarrier(DeliveryCarrierRequest, file);
        resp.setMessage("Register successful");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeliveryCarrier(@PathVariable Long id,
                                                   @RequestPart String req,
                                                   @RequestPart("file") MultipartFile file) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        ObjectMapper mapper = new ObjectMapper();
        CreateDeliveryCarrierRequest DeliveryCarrierRequest = null;
        try{
            DeliveryCarrierRequest =mapper.readValue(req,CreateDeliveryCarrierRequest.class);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Invalid teacherRequest JSON");
        }
        deliveryService.UpdateDeliveryCarrier(id, DeliveryCarrierRequest, file);
        resp.setMessage("update Delivery Carrier successful");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeliveryCarrier(@PathVariable Long id) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        deliveryService.DeleteDeliveryCarrier(id);
        resp.setMessage("delete Delivery Carrier successful");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> findAllDeliveryCarrier() {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        List<DeliveryReponse> reponses= deliveryService.FindAllDeliveryCarriers();
        resp.setData(reponses);
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }

}
