package com.bojken.ws_projektarbete_6.service;

import com.bojken.ws_projektarbete_6.model.CustomUser;
import com.bojken.ws_projektarbete_6.repository.UserRepository;
import com.bojken.ws_projektarbete_6.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUser(String username) {
        // Hitta användaren och ta bort den från databasen
        CustomUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userRepository.delete(user);
    }

    @Service()
    public static class CustomUserDetailsService implements UserDetailsService {

        private final UserRepository userRepository;

        @Autowired
        public CustomUserDetailsService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            CustomUser customUser = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

            System.out.println("---DEBUGGING LOGIN---");
            System.out.println("IT WENT THROUGH");
            System.out.println("---DEBUGGING LOGIN---");

            return new CustomUserDetails(customUser);
        }
    }
}
