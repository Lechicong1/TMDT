package TMDT.example.TMDT.Users.Service;

import TMDT.example.TMDT.Users.DTO.Request.AddressRequest;
import TMDT.example.TMDT.Users.DTO.Response.AddressDTO;

import java.util.List;

public interface AddressService {
    public void addAddress(AddressRequest address);
    public List<AddressDTO> getAllAddressByUsers();
    public void deleteAddress(Long id);
    public void updateAddress(Long id,AddressRequest address);
}
