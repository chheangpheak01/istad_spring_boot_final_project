package com.sopheak.istadfinalems.controller;
import com.sopheak.istadfinalems.model.dto.address.AddressCreateDto;
import com.sopheak.istadfinalems.model.dto.address.AddressUpdateDto;
import com.sopheak.istadfinalems.service.AddressService;
import com.sopheak.istadfinalems.utils.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;

@RestController
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('user')")
    public ResponseTemplate<Object> getAddressByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Address data retrieved successfully")
                .data(addressService.getAddressByUuid(uuid))
                .build();
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasRole('user')")
    public ResponseTemplate<Object> getAllAddressByPagination(Pageable pageable){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Address data retrieved by pagination successfully")
                .data(addressService.getAllAddressByPagination(pageable))
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('admin','super_admin')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTemplate<Object> createAddress(@RequestBody @Validated AddressCreateDto addressCreateDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.CREATED.toString())
                .message("Address has been created successfully")
                .data(addressService.createAddress(addressCreateDto))
                .build();
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('admin','super_admin')")
    public ResponseTemplate<Object> updateAddressByUuid(@PathVariable String uuid, @RequestBody @Validated AddressUpdateDto addressUpdateDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Address has been updated successfully")
                .data(addressService.updateAddressByUuid(uuid, addressUpdateDto))
                .build();
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('admin','super_admin')")
    public ResponseTemplate<Object> deleteAddressByUuid(@PathVariable String uuid) {
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message(addressService.deleteAddressByUuid(uuid))
                .data(null)
                .build();
    }
}
