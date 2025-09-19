package TMDT.example.TMDT.Shop.Controller;

import TMDT.example.TMDT.Shop.Payload.Reponse.ShopReponse;
import TMDT.example.TMDT.Shop.Payload.Request.ShopRequest;
import TMDT.example.TMDT.Shop.Service.ShopService;
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
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;
    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping()
    public ResponseEntity<?> updateShop(
                                        @RequestPart String req,
                                        @RequestPart("file") MultipartFile file) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        ObjectMapper mapper = new ObjectMapper();
        ShopRequest shopRequest  = null;
        try {
            shopRequest = mapper.readValue(req, ShopRequest.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Invalid teacherRequest JSON");
        }
        shopService.updateShop(shopRequest, file);
        resp.setMessage("update shop successful");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable Long id) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        shopService.deleteShop(id);
        resp.setMessage("delete shop successful");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> findAllShop() {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        List<ShopReponse> reponses = shopService.getAllShops();
        resp.setData(reponses);
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("/myShop")
    public ResponseEntity<?> findMyShop() {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        ShopReponse shopReponse= shopService.myShop();
        resp.setData(shopReponse);
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
}
