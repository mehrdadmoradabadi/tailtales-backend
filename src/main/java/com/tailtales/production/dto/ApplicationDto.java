package com.tailtales.production.dto;

import com.tailtales.production.application.Status;
import com.tailtales.production.pet.Pet;
import com.tailtales.production.shelter.Shelter;
import com.tailtales.production.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApplicationDto {
    private User user;
    private Shelter shelter;
    private Pet pet;
    private Status status;
    private LocalDateTime date;
    private String comments;
    private LocalDateTime outcomeDate;
}
