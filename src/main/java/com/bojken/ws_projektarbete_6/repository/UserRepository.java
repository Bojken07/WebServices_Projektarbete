package com.bojken.ws_projektarbete_6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {

    // Queries
    Optional<CustomUser> findByUsername(String username);

}