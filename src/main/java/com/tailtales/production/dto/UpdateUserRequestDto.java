package com.tailtales.production.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserRequestDto {
    private String firstName;
    private String lastName;
    private String profilePicture;
    private String password;
}
