package TMDT.example.TMDT.Auth.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

//    private final AuthenticationManager authenticationManager;
//    private final CreateJwt jwtCustom;
//
//    @PostMapping()
//    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
//        HttpStatus status = HttpStatus.OK;
//        // Tạo đối tượng phản hồi
//        ResponseData responseData = new ResponseData();
//        try {
//            // Tạo đối tượng xác thực từ username và password
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
//
//            // Xác thực người dùng
//            Authentication authentication = authenticationManager.authenticate(authenticationToken);
//
//            // Lưu thông tin xác thực vào SecurityContext
//            if (authentication.isAuthenticated()) {
//                SecurityContextHolder.getContext().setAuthentication(authentication);
    //                String token = jwtCustom.createToken(authentication);
//                responseData.setSuccess(true);
//                responseData.setMessage("Login successful");
//                responseData.setData(token);
//            }
//        } catch (AuthenticationException ex) {
//            // Trường hợp xác thực thất bại
//            responseData.setSuccess(false);
//            responseData.setMessage("Invalid username or password");
//            responseData.setData(null);
//            status=HttpStatus.UNAUTHORIZED;
//        }
//        return  ResponseEntity.status(status).body(responseData);
//    }

}
