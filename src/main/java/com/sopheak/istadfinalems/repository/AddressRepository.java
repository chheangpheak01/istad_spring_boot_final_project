package com.sopheak.istadfinalems.repository;
import com.sopheak.istadfinalems.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findByUuidAndIsDeletedFalse(String uuid);
    Page<Address> findAllByIsDeletedFalse(Pageable pageable);
}
