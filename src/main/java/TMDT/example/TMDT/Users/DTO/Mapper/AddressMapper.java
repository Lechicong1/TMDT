package TMDT.example.TMDT.Users.DTO.Mapper;

import TMDT.example.TMDT.Users.DTO.Response.AddressDTO;
import TMDT.example.TMDT.Users.Entity.AddressEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    List<AddressDTO> toDTOList(List<AddressEntity> entity);
}
