package TMDT.example.TMDT.Users.DTO.Mapper;

import TMDT.example.TMDT.Users.DTO.Response.UserDTO;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDTO> toDtoList(List<UserEntity> listEntity);
    UserEntity toEntity(UserDTO userDTO);
}

