package TMDT.example.TMDT.Shop.Controller;

import TMDT.example.TMDT.Shop.Payload.Reponse.RegisterSellerReponse;
import TMDT.example.TMDT.Shop.Payload.Request.RegisterSellerRequest;
import TMDT.example.TMDT.Shop.Service.SellerRegisterService;
import TMDT.example.TMDT.Respone.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registerSeller")
public class RegisterSeller {
    private final SellerRegisterService sellerRegisterService;
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<?> registerSeller(@RequestPart String req,
                                          @RequestPart("file") MultipartFile file) {
        ResponseData resp = new ResponseData();

        HttpStatus httpStatus = HttpStatus.CREATED;
        ObjectMapper mapper = new ObjectMapper();
        RegisterSellerRequest  request = null;
        try {
            request = mapper.readValue(req, RegisterSellerRequest.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Invalid teacherRequest JSON");
        }
        sellerRegisterService.RegisterSeller(request,file);
        resp.setMessage("Register successful");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{idRegister}")
    public ResponseEntity<?> confirm( @PathVariable Long idRegister,@RequestParam int option) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.CREATED;
        sellerRegisterService.confirmSeller(idRegister,option);
        resp.setMessage("Update successful");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/pending")
    public ResponseEntity<?> getSellerRegisterStatusPending() {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        List<RegisterSellerReponse> list = sellerRegisterService.getAllPendingSellerRegister();
        resp.setData(list);
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/approved")
    public ResponseEntity<?> getSellerRegisterStatusApproved() {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        List<RegisterSellerReponse> list = sellerRegisterService.getAllApprovedSellerRegister();
        resp.setData(list);
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
}
