package com.tailtales.production.pet;

import com.tailtales.production.shelter.Shelter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity (name = "pets")
@Data
@NoArgsConstructor
@ToString
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petId")
    private Integer petId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "registerNumber", nullable = false,unique = true)
    private Integer registerNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "description")
    private String description;

    @Column(name = "images")
    private List<String> images;

    @Column(name = "species")
    private String species;

    @Column(name = "color")
    private String color;

    @Column(name = "size")
    private String size;

    @Column(name = "isVaccinated")
    private Boolean isVaccinated;

    @Column(name = "isNeuteredSpayed")
    private Boolean isNeuteredSpayed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelterId")
    private Shelter shelter;

    public Pet(String name, String breed, Integer age, Gender gender, String description, List<String> images, String species, String color, String size, Boolean isVaccinated, Boolean isNeuteredSpayed, Shelter shelter) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.description = description;
        this.images = images;
        this.species = species;
        this.color = color;
        this.size = size;
        this.isVaccinated = isVaccinated;
        this.isNeuteredSpayed = isNeuteredSpayed;
        this.shelter = shelter;
    }
}
