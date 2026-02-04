package com.sopheak.istadfinalems.service;
import com.sopheak.istadfinalems.entities.Address;
import com.sopheak.istadfinalems.entities.emun.AuditAction;
import com.sopheak.istadfinalems.exception.AddressNotFoundException;
import com.sopheak.istadfinalems.mapper.AddressMapStruct;
import com.sopheak.istadfinalems.model.dto.address.AddressCreateDto;
import com.sopheak.istadfinalems.model.dto.address.AddressResponseDto;
import com.sopheak.istadfinalems.model.dto.address.AddressUpdateDto;
import com.sopheak.istadfinalems.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;
    private final AddressMapStruct addressMapStruct;
    private final AuditService auditService;

    @Override
    public AddressResponseDto getAddressByUuid(String uuid) {
        Optional<Address> address = addressRepository.findByUuidAndIsDeletedFalse(uuid);
        if (address.isEmpty()) {
            throw new AddressNotFoundException("Address not found with this uuid: " + uuid);
        }
        return addressMapStruct.mapFromAddressToAddressResponseDto(address.get());
    }

    @Override
    public Page<AddressResponseDto> getAllAddressByPagination(Pageable pageable) {
        Page<Address> addressPage =addressRepository.findAllByIsDeletedFalse(pageable);
        if (addressPage.isEmpty()) {
            throw new AddressNotFoundException("No Address found for this page");
        }
        return addressPage.map(addressMapStruct::mapFromAddressToAddressResponseDto);
    }

    @Override
    public AddressResponseDto createAddress(AddressCreateDto addressCreateDto) {

        Address address = new Address();

        address.setUuid(UUID.randomUUID().toString());
        address.setStreet(addressCreateDto.street());
        address.setCity(addressCreateDto.city());
        address.setProvince(addressCreateDto.province());
        address.setCountry(addressCreateDto.country());
        address.setIsDeleted(false);
        addressRepository.save(address);
        auditService.log(
                "Address",
                address.getId().toString(),
                AuditAction.CREATE,
                "Created address in city: " + address.getCity()
        );
        return addressMapStruct.mapFromAddressToAddressResponseDto(address);
    }

    @Override
    public AddressResponseDto updateAddressByUuid(String uuid, AddressUpdateDto addressUpdateDto) {
        Optional<Address> address = addressRepository.findByUuidAndIsDeletedFalse(uuid);
        if(address.isEmpty()){
            throw new AddressNotFoundException("No Address found with this uuid: " + uuid);
        }
        address.get().setStreet(addressUpdateDto.street());
        address.get().setCity(addressUpdateDto.city());
        address.get().setProvince(addressUpdateDto.province());
        address.get().setCountry(addressUpdateDto.country());
        address.get().setIsDeleted(addressUpdateDto.isDeleted());
        addressRepository.save(address.get());
        auditService.log(
                "Address",
                address.get().getId().toString(),
                AuditAction.UPDATE,
                "Updated address details for city: " + address.get().getCity()
        );
        return addressMapStruct.mapFromAddressToAddressResponseDto(address.get());
    }

    @Override
    public String deleteAddressByUuid(String uuid) {
        Optional<Address> address = addressRepository.findByUuidAndIsDeletedFalse(uuid);
        if(address.isEmpty()){
            throw new AddressNotFoundException("Address not found with this uuid: " + uuid);
        }
        address.get().setIsDeleted(true);
        addressRepository.save(address.get());
        auditService.log(
                "Address",
                address.get().getId().toString(),
                AuditAction.DELETE,
                "Soft deleted address: " + address.get().getCity()
        );
        return "Address with UUID " + uuid + " has been deleted successfully";
    }
}
