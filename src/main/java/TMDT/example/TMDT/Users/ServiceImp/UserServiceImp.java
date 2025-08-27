package TMDT.example.TMDT.Users.ServiceImp;

import TMDT.example.TMDT.Exception.ResourceNotFoundException;
import TMDT.example.TMDT.Users.DTO.Mapper.UserMapperImpl;
import TMDT.example.TMDT.Users.DTO.Request.UserRequest;
import TMDT.example.TMDT.Users.DTO.Response.UserDTO;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import TMDT.example.TMDT.Users.Repository.UserRepo;
import TMDT.example.TMDT.Users.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {
    private final UserRepo userRepo;
    private final UserMapperImpl userMapper;
    private final PasswordEncoder passwordEncoder;
    private String generateAutoUsername() {
        String prefix = "user";
        int counter = 1;
        String username;

        do {
            username = prefix + counter; // ví dụ: user1, user2, user3,...
            counter++;
        } while (userRepo.existsByUsername(username));

        return username;
    }
    @Override
    public void createUser(UserRequest req) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(generateAutoUsername());
        userEntity.setPassword(passwordEncoder.encode(req.getPassword()));
        if(req.getEmail() != null) {
            userEntity.setEmail(req.getEmail());
            userEntity.setVerified(false);
        }
        if(req.getPhone() != null) {
            userEntity.setPhone(req.getPhone());
            userEntity.setVerified(true);
        }
        userEntity.setCreatedAt(LocalDateTime.now());

        userRepo.save(userEntity);
    }

    @Override
    public void updateUser(Long idUser, UserRequest req) {
        UserEntity userEntity = userRepo.findById(idUser).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        if (req.getUsername() != null) userEntity.setUsername(req.getUsername());
        if (req.getEmail() != null) userEntity.setEmail(req.getEmail());
        if (req.getPassword() != null) userEntity.setPassword(req.getPassword());
        if (req.getPhone() != null) userEntity.setPhone(req.getPhone());
        userEntity.setUpdatedAt(LocalDateTime.now());
        userRepo.save(userEntity);
    }

    @Override
    public void deleteUser(Long idUser) {
        if (!userRepo.existsById(idUser)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepo.deleteById(idUser);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserEntity> users = userRepo.findAll();
        return userMapper.toDtoList(users);
    }
}
