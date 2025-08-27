package TMDT.example.TMDT.Users.Controller;

import TMDT.example.TMDT.Users.DTO.Request.UserRequest;
import TMDT.example.TMDT.Users.DTO.Response.ResponseData;
import TMDT.example.TMDT.Users.DTO.Response.UserDTO;
import TMDT.example.TMDT.Users.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<?> createUser( @RequestBody  UserRequest userRequest) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.CREATED;
        userService.createUser(userRequest);
        resp.setMessage("User created successfully");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateUser( @PathVariable Long id,@RequestBody UserRequest userRequest) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        userService.updateUser(id,userRequest);
        resp.setMessage("User update successfully");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteUser( @PathVariable Long id) {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        userService.deleteUser(id);
        resp.setMessage("User deleted successfully");
        resp.setSuccess(true);
        return ResponseEntity.status(httpStatus).body(resp);
    }
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        ResponseData resp = new ResponseData();
        HttpStatus httpStatus = HttpStatus.OK;
        List<UserDTO> listUsers = userService.findAllUsers();
        resp.setSuccess(true);
        resp.setData(listUsers);
        return ResponseEntity.status(httpStatus).body(resp);
    }
}
