package com.tailtales.production.dto;

import com.tailtales.production.pet.Pet;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ShelterDto {
    private String name;
    private String location;
    private String contactInfo;
    private String website;
    private String email;
    private String phone;
    private List<Pet> pets;

}
