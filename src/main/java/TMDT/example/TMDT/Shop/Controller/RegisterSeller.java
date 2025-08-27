package TMDT.example.TMDT.Shop.Controller;

import TMDT.example.TMDT.Shop.Payload.Request.RegisterSellerRequest;
import TMDT.example.TMDT.Shop.Service.SellerRegisterService;
import TMDT.example.TMDT.Users.DTO.Request.AddressRequest;
import TMDT.example.TMDT.Users.DTO.Response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registerSeller")
public class RegisterSeller {
    private final SellerRegisterService sellerRegisterService;
    @PostMapping
    public ResponseEntity<?> registerSeller(@RequestBody RegisterSellerRequest req,
                                          @RequestPart("file") MultipartFile file) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.CREATED;
        sellerRegisterService.RegisterSeller(req,file);
        resp.setMessage("Register successful");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @PutMapping(value = "/{idRegister}")
    public ResponseEntity<?> confirm( @PathVariable Long idRegister,@RequestParam int option) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.CREATED;
        sellerRegisterService.confirmSeller(idRegister,option);
        resp.setMessage("Update successful");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
}
