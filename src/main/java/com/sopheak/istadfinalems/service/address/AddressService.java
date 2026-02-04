package com.sopheak.istadfinalems.service.address;
import com.sopheak.istadfinalems.model.dto.address.AddressCreateDto;
import com.sopheak.istadfinalems.model.dto.address.AddressResponseDto;
import com.sopheak.istadfinalems.model.dto.address.AddressUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    AddressResponseDto getAddressByUuid(String uuid);
    Page<AddressResponseDto> getAllAddressByPagination(Pageable pageable);
    AddressResponseDto createAddress(AddressCreateDto addressCreateDto);
    AddressResponseDto updateAddressByUuid(String uuid, AddressUpdateDto addressUpdateDto);
    String deleteAddressByUuid(String uuid);
}
