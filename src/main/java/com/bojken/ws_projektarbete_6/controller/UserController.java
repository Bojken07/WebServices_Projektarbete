package com.bojken.ws_projektarbete_6.controller;

import ch.qos.logback.core.model.Model;
import com.bojken.ws_projektarbete_6.authorities.UserRole;
import com.bojken.ws_projektarbete_6.model.CustomUser;
import com.bojken.ws_projektarbete_6.model.Movie;
import com.bojken.ws_projektarbete_6.model.UserDTO;
import com.bojken.ws_projektarbete_6.repository.MovieRepository;
import com.bojken.ws_projektarbete_6.repository.UserRepository;
import com.bojken.ws_projektarbete_6.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MovieRepository movieRepository;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        return "index";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {

        model.addAttribute("userDTO", new UserDTO());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute(name = "userDTO") UserDTO userDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            model.addAttribute("usernameError", "Username is already taken");
            return "register";
        }

        try {
            CustomUser newUser = new CustomUser(
                    userDTO.getUsername(),
                    passwordEncoder.encode(userDTO.getPassword()),
                    userDTO.getUserRole() != null ? userDTO.getUserRole() : UserRole.USER, // Sätt en standard roll om ingen anges
                    true,
                    true,
                    true,
                    true
            );

            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("usernameError", "Användarnamnet är redan taget.");
            return "register";
        }

        return "redirect:/";
    }

    @GetMapping("/delete-user")
    public String showDeleteUserPage() {
        return "delete-user";
    }

    // Hanterar den faktiska borttagningen av användaren
    @PostMapping("/delete-user")
    public String deleteUser(HttpServletRequest request) {
        // Hämta den inloggade användaren
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Ta bort användaren
        userService.deleteUser(username);

        // Logga ut användaren
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();

        // Redirecta till startsidan eller inloggningssidan
        return "redirect:/login?deleted=true";
    }

}