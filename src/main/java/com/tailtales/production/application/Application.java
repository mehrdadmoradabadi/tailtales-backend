package com.tailtales.production.application;

import com.tailtales.production.pet.Pet;
import com.tailtales.production.shelter.Shelter;
import com.tailtales.production.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "application")
@Table(name = "application")
@Data
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicationId")
    private Integer applicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelterId")
    private Shelter shelterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petId")
    private Pet petId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "comments")
    private String comments;

    @Column(name = "outcomeDate")
    private LocalDateTime outcomeDate;

    public Application(User user, Shelter shelter, Pet pet, Status status, LocalDateTime date, String comments, LocalDateTime outcomeDate) {
        this.userId = user;
        this.shelterId = shelter;
        this.petId = pet;
        this.status = status;
        this.date = date;
        this.comments = comments;
        this.outcomeDate = outcomeDate;
    }
}
