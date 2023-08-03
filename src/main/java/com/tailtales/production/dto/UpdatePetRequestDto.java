package com.tailtales.production.dto;

import com.tailtales.production.pet.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdatePetRequestDto {
    private String name;
    private String breed;
    private Integer age;
    private Gender gender;
    private String description;
    private List<String> images;
    private String species;
    private Integer shelterId;
    private String color;
    private Boolean isNeuteredSpayed;
    private Boolean isVaccinated;
    private String size;
}
