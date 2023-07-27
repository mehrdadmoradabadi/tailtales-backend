package com.tailtales.production.dto;

import com.tailtales.production.user.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String profilePicture;
    private Role role;
}
