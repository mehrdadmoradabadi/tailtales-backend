package com.tailtales.production.admin;

import com.tailtales.production.user.Role;
import com.tailtales.production.user.User;
import com.tailtales.production.user.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultAdmin {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostConstruct
    public void initializeDefaultAdmin() {
        if (!userService.existByUsername("admin@tailtales.com")) {
            User admin = new User();
            admin.setUsername("admin@tailtales.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setProfilePicture("");
            userService.signUp(admin);

        }
    }
}

