package TMDT.example.TMDT.Config;

import TMDT.example.TMDT.Users.Entity.RolesEntity;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import TMDT.example.TMDT.Users.Entity.UserRolesEntity;
import TMDT.example.TMDT.Users.Repository.UserRepo;
import TMDT.example.TMDT.Users.Repository.UserRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Không tìm thấy username");
        }
        List<UserRolesEntity> listRoles=userRoleRepo.findAllByUser(user);
        List<GrantedAuthority> authorities = listRoles.stream()
                .map(ur -> new SimpleGrantedAuthority(ur.getRole().getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
               authorities);
    } // tao ra doi tuong User cua spring de xac thuc va phan quyen nguoi dung
    }

