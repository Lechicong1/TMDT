package TMDT.example.TMDT.Security;

import TMDT.example.TMDT.Users.Repository.UserRepo;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Component
public class CreateJwt {
    @Value("${spring.security.authentication.jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds;
    @Value("${spring.security.authentication.jwt.base64-secret}")
    private String secretKey;
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
    private final UserRepo userRepo;
    private final JwtEncoder jwtEncoder;

    //giai ma secretkey
    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(keyBytes,0,keyBytes.length,JWT_ALGORITHM.getName());
    }

    @Bean
    public JwtEncoder JwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()) );
    }
    // giai ma token duoc gui tu header
    @Bean
    public JwtDecoder JwtDecoder() {    // xac thuc chu ky so
        NimbusJwtDecoder  jwtDecoder=NimbusJwtDecoder.withSecretKey(
                getSecretKey()).macAlgorithm(JWT_ALGORITHM).build();
        return token -> {
            try{
                return jwtDecoder.decode(token);
            }
            catch(Exception e){
                return null;
            }
        };
    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("authorities"); // claim bạn thêm vào token
        authoritiesConverter.setAuthorityPrefix(""); // nếu bạn đã gắn "ROLE_" trong token rồi

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }
    public String createToken(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException("Authentication cannot be null");
        }

        Instant now = Instant.now();
        Instant validity=now.plus(tokenValidityInSeconds, ChronoUnit.SECONDS);
        // Lấy danh sách quyền (authorities)
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role
                        .replaceFirst("^SCOPE_", "")        // bỏ tiền tố SCOPE_
                        .replaceFirst("^OAUTH2_", "")       // bỏ tiền tố OAUTH2_
                        .replaceFirst("^ROLE_", "")         // tùy chọn: nếu bạn không muốn ROLE_
                )
                .collect(Collectors.toList());
        Long user_id = userRepo.findIdUserByUsername(authentication.getName());
        JwtClaimsSet claims= JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(user_id.toString())
                .claim("authorities",authorities)
                .build();
        JwsHeader jwsHeader=JwsHeader.with(JWT_ALGORITHM).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,claims)).getTokenValue();  // tao chu ky so gom khoa bi mat va thong diep(payload)
    }
}
