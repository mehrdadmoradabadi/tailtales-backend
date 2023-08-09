package com.tailtales.production.auth;

import com.tailtales.production.exceptions.authentication.AuthenticationExceptions;
import com.tailtales.production.exceptions.login.Exceptions;
import com.tailtales.production.user.*;
import com.tailtales.production.utils.ApiResponse;
import com.tailtales.production.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    private void validateUserObject(AuthCreateUserRequestDto user) {
        if (user.getUsername() == null || user.getPassword() == null || user.getRole() == null) {
            throw new Exceptions.InvalidUserObjectException("Invalid user object. Required fields are missing.");
        }
    }
    private User mapFromUserDto (AuthCreateUserRequestDto newUser){
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setProfilePicture(newUser.getProfilePicture());
        user.setPassword(newUser.getPassword());
        user.setLastName(newUser.getLastName());
        user.setFirstName(newUser.getFirstName());
        user.setRole(newUser.getRole());
        user.setProfilePicture(newUser.getProfilePicture());
        return user;
    }
    @Operation(summary = "Add new user ", description = "Adds new user to database you need to send a User object.")
    @PostMapping(path = "/signup", consumes = {"application/json"})
    public ResponseEntity<?> signUp(@RequestBody AuthCreateUserRequestDto newUser) {
        try{
        validateUserObject(newUser);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRole(Role.USER);
        return ResponseEntity.ok(new ApiResponse<>("200", "Successful", userService.signUp(mapFromUserDto(newUser))));
        }catch (Exceptions.UserAlreadyExistsException ex) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exist");
        }catch (Exceptions.InvalidUserObjectException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user object");
        }

    }

    @Operation(summary = "Login method", description = "Use this method to login and get the token. this token is necessary for all the method endpoints." +
            "usage: add an Authorization header to all your requests with the value like this 'Bearer +THE TOKEN'")

    @PostMapping(path = "/login", consumes = {"application/json"})
    public String login(@RequestBody AuthRequest authRequest) throws AuthenticationExceptions {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        User user = userRepository.findByUsername(authRequest.getUsername());

        return jwtUtils.generateToken(user);
    }
}
