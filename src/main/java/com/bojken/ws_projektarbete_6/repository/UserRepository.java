package com.bojken.ws_projektarbete_6.repository;

import com.bojken.ws_projektarbete_6.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {

    // Queries
    Optional<CustomUser> findByUsername(String username);

}