package com.sopheak.istadfinalems.mapper;
import com.sopheak.istadfinalems.entities.Address;
import com.sopheak.istadfinalems.model.dto.address.AddressResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapStruct {
    AddressResponseDto mapFromAddressToAddressResponseDto(Address address);
}
