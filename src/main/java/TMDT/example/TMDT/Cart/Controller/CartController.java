package TMDT.example.TMDT.Cart.Controller;

import TMDT.example.TMDT.Cart.DTO.CartRequest;
import TMDT.example.TMDT.Cart.Service.CartService;
import TMDT.example.TMDT.Products.Payload.Request.CateGoryRequest;
import TMDT.example.TMDT.Respone.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/Cart")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SELLER','USER')")
public class CartController {
    private final CartService cartService;

    @PostMapping
    ResponseEntity<?> addItemToCart(@RequestBody CartRequest cartRequest) {
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.CREATED;
        cartService.addItemToCart(cartRequest);
        responseData.setMessage("cart has been successfully created");
        responseData.setSuccess(true);
        return ResponseEntity.status(status).body(responseData);
    }
    @DeleteMapping
    ResponseEntity<?> DeleteItemToCart(Long cartId) {
        ResponseData responseData = new ResponseData();
        HttpStatus status = HttpStatus.CREATED;
        cartService.removeItemFromCart(cartId);
        responseData.setMessage("cart has been successfully deleted");
        responseData.setSuccess(true);
        return ResponseEntity.status(status).body(responseData);
    }

}
