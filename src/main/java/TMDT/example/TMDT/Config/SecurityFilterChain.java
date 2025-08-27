package TMDT.example.TMDT.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityFilterChain {
    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    private final CustomUserDetailService customUserDetailService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public org.springframework.security.web.SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ✅ Cho phép preflight CORS
                        .requestMatchers("/api/login","/api/register","/api/courses/{courseId}","/api/otp/**").permitAll()
                        .requestMatchers("/upload/**","/api/contact","/api/courses/languages").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/courses").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/teachers").permitAll()

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder) // gọi hàm JwtDecoder đã khai báo @Bean
                                .jwtAuthenticationConverter(jwtAuthenticationConverter) // gọi hàm phân quyền
                        )

                )
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/oauth2/authorization/google")
//                        .successHandler(successHandler)  // custom xử lý sau khi đăng nhập thành công
//
//                )
                .logout(logout -> logout.disable());



        return http.build();
    }
    @Value("${app.cors.allowed-origin}")
    private String allowOrigin;
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(allowOrigin)); // hoặc 127.0.0.1 nếu dùng IP
        // ✅ Cho tất cả domain, phù hợp cho máy ảo, máy thật
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // ✅ Cho phép gửi token/cookie

        // (Tuỳ chọn) In ra log kiểm tra
        System.out.println("✅ CORS config đã được apply");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }
}

