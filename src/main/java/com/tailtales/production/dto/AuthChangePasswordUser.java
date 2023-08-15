package com.tailtales.production.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthChangePasswordUser {
    private String username;
    private String oldPassword;
    private String newPassword;
}
