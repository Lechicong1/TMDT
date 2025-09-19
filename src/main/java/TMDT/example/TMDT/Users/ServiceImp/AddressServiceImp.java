package TMDT.example.TMDT.Users.ServiceImp;

import TMDT.example.TMDT.Exception.ResourceNotFoundException;
import TMDT.example.TMDT.Users.DTO.Mapper.AddressMapper;

import TMDT.example.TMDT.Users.DTO.Request.AddressRequest;
import TMDT.example.TMDT.Users.DTO.Response.AddressDTO;
import TMDT.example.TMDT.Users.Entity.AddressEntity;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import TMDT.example.TMDT.Users.Repository.AddressRepo;
import TMDT.example.TMDT.Users.Repository.UserRepo;
import TMDT.example.TMDT.Users.Service.AddressService;
import TMDT.example.TMDT.Users.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressServiceImp implements AddressService {
    private final AddressMapper addressMapper;
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;
    @Override
    public void addAddress(AddressRequest req) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        UserEntity usersCur = userRepo.findByUsername(username);
        if(usersCur == null) {
            throw new ResourceNotFoundException("User not found");
        }
        if (req.isAddressDefault()) {
            addressRepo.clearDefaultForUser(usersCur.getId());
        }

        AddressEntity addressEntity = AddressEntity.builder()
                .user(usersCur)
                .recipientName(req.getRecipientName())
                .recipientPhone(req.getRecipientPhone())
                .district(req.getDistrict())
                .streetAddress(req.getStreetAddress())
                .ward(req.getWard())
                .province(req.getProvince())
                .createdAt(LocalDateTime.now())
                .addressDefault(req.isAddressDefault())
                .build();
        addressRepo.save(addressEntity);
    }

    @Override
    public List<AddressDTO> getAllAddressByUsers() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        UserEntity usersCur = userRepo.findByUsername(username);
        if(usersCur == null) {
            throw new ResourceNotFoundException("User not found");
        }
        List<AddressEntity> addressEntities = addressRepo.findAllByUser(usersCur);

        return addressMapper.toDTOList(addressEntities);
    }

    @Override
    public void deleteAddress(Long id) {
        if(!addressRepo.existsById(id)) {
            throw new ResourceNotFoundException("Address not found");
        }
        addressRepo.deleteById(id);
    }

    @Override
    public void updateAddress(Long id, AddressRequest req) {
        AddressEntity addressEntity = addressRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        if(req.getRecipientName() != null) addressEntity.setRecipientName(req.getRecipientName());
        if(req.getRecipientPhone() != null) addressEntity.setRecipientPhone(req.getRecipientPhone());
        if(req.getDistrict() != null) addressEntity.setDistrict(req.getDistrict());
        if(req.getStreetAddress() != null) addressEntity.setStreetAddress(req.getStreetAddress());
        if(req.getWard() != null) addressEntity.setWard(req.getWard());
        if(req.getProvince() != null) addressEntity.setProvince(req.getProvince());
        if (req.isAddressDefault()) {
            addressRepo.clearDefaultForUser(addressEntity.getUser().getId());
            addressEntity.setAddressDefault(true);
        }
        else{
            addressEntity.setAddressDefault(false);
        }

        addressRepo.save(addressEntity);

    }
}
