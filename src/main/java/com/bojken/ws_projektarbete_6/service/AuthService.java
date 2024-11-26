package com.bojken.ws_projektarbete_6.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public void logout(HttpServletRequest request) {
        // Får tillgång till HTTP-sessionen
        HttpSession session = request.getSession(false);

        // Om en session existerar, invalidera den
        if (session != null) {
            session.removeAttribute("currentUser"); // Ta bort användardata
            session.invalidate(); // Invalidera sessionen
            System.out.println("Logged out successfully");
        }
    }
}
