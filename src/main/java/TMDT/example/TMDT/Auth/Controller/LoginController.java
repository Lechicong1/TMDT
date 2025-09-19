package TMDT.example.TMDT.Auth.Controller;

import TMDT.example.TMDT.Auth.DTO.Request.LoginRequest;
import TMDT.example.TMDT.Security.CreateJwt;
import TMDT.example.TMDT.Respone.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final CreateJwt jwtCustom;

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        HttpStatus status = HttpStatus.OK;
        // Tạo đối tượng phản hồi
        ResponseData responseData = new ResponseData();
        try {
            // Tạo đối tượng xác thực từ username và password
            String emailOrPhone = loginRequest.getEmail()==null?loginRequest.getPhone():loginRequest.getEmail();
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(emailOrPhone, loginRequest.getPassword());
            // Xác thực người dùng
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Lưu thông tin xác thực vào SecurityContext
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = jwtCustom.createToken(authentication);
                responseData.setSuccess(true);
                responseData.setMessage("Login successful");
                responseData.setData(token);
            }
        } catch (AuthenticationException ex) {
            // Trường hợp xác thực thất bại
            responseData.setSuccess(false);
            responseData.setMessage("Invalid username or password");
            responseData.setData(null);
            status = HttpStatus.UNAUTHORIZED;
        }
        return ResponseEntity.status(status).body(responseData);
    }

}
