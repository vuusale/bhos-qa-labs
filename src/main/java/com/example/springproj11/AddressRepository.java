package com.example.springproj11;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, String> {
    Address findByName(String name);

//    Optional<Address> findById(UUID id);
    Address findById(UUID id);
}
