package TMDT.example.TMDT.Users.Controller;

import TMDT.example.TMDT.Users.DTO.Request.AddressRequest;
import TMDT.example.TMDT.Users.DTO.Request.UserRequest;
import TMDT.example.TMDT.Users.DTO.Response.AddressDTO;
import TMDT.example.TMDT.Users.DTO.Response.ResponseData;
import TMDT.example.TMDT.Users.DTO.Response.UserDTO;
import TMDT.example.TMDT.Users.Service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;
    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody AddressRequest addressRequest) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.CREATED;
        addressService.addAddress(addressRequest);
        resp.setMessage("Address created successfully");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateAddress( @PathVariable Long id,@RequestBody AddressRequest addressRequest) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        addressService.updateAddress(id,addressRequest);
        resp.setMessage("Address update successfully");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteUser( @PathVariable Long id) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        addressService.deleteAddress(id);
        resp.setMessage("Address deleted successfully");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        List<AddressDTO> listAddress = addressService.getAllAddressByUsers();
        resp.setSuccess(true);
        resp.setData(listAddress);
        return ResponseEntity.status(httpStatus).body(resp);
    }
}
