package com.sopheak.istadfinalems.mapper;
import com.sopheak.istadfinalems.entities.Address;
import com.sopheak.istadfinalems.model.dto.address.AddressResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapStruct {
    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "street", target = "street")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "province", target = "province")
    @Mapping(source = "country", target = "country")
    AddressResponseDto mapFromAddressToAddressResponseDto(Address address);
}
