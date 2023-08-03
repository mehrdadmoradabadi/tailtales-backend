package com.tailtales.production.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserRequestDto {
    private String firstName;
    private String lastName;
    private String username;
    private String profilePicture;
    private String password;
}
