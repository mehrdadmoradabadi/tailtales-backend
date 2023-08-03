package com.tailtales.production.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantDto {
    private String image;
    private Integer id;
    private String username;

    public ParticipantDto(String image, Integer id,String username) {
        this.image = image;
        this.id = id;
        this.username = username;
    }

    // Getters and setters (if needed)
}
