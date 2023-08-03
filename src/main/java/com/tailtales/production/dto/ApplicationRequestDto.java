package com.tailtales.production.dto;

import com.tailtales.production.application.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApplicationRequestDto {
    private Integer userId;
    private Integer shelterId;
    private Integer petRegisterId;
    private Status status;
    private String comments;
    private LocalDateTime outcomeDate;
}
