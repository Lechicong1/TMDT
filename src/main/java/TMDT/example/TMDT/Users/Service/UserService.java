package TMDT.example.TMDT.Users.Service;

import TMDT.example.TMDT.Users.DTO.Request.UserRequest;
import TMDT.example.TMDT.Users.DTO.Response.UserDTO;
import TMDT.example.TMDT.Users.Entity.UserEntity;

import java.util.List;

public interface UserService {
    void createUser(UserRequest user);

    void updateUser(Long idUser, UserRequest user);
    void deleteUser(Long id);
    List<UserDTO> findAllUsers();

}
