package com.tailtales.production.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantDto {
    private String image;
    private Integer id;
    private String username;
    private String fullName;

    public ParticipantDto(String image, Integer id,String username,String fullName) {
        this.image = image;
        this.id = id;
        this.username = username;
        this.fullName = fullName;
    }

    // Getters and setters (if needed)
}
