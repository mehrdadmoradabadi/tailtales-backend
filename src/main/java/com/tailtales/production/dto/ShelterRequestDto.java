package com.tailtales.production.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShelterRequestDto {
    private String name;
    private String email;
    private String website;
    private String phone;
    private String contactInfo;
    private String location;
    private Integer shelterAdminId;
}
