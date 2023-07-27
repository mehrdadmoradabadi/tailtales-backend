package com.tailtales.production.shelter;

import com.tailtales.production.pet.Pet;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "shelters")
@Table(name = "shelters")
@Data
@NoArgsConstructor
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shelterId")
    private Integer shelterId;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "contactInfo")
    private String contactInfo;

    @Column(name = "website")
    private String website;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();

    public Shelter(Integer shelterId, String name, String location, String contactInfo, String website, String email, String phone) {
        this.shelterId = shelterId;
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
        this.website = website;
        this.email = email;
        this.phone = phone;
    }
}
