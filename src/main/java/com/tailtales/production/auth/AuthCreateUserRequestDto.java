package com.tailtales.production.auth;

import com.tailtales.production.user.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthCreateUserRequestDto {
    private String firstName;
    private String lastName;
    private String username;
    private String profilePicture;
    private String password;
    private Role role;

    public AuthCreateUserRequestDto(String firstName, String lastName, String username, String profilePicture, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.profilePicture = profilePicture;
        this.password = password;
        this.role = role;
    }
}
