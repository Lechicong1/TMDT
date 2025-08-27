package TMDT.example.TMDT.Security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomUserPrincipal {
    private final String userId;
    private final String username;

}
