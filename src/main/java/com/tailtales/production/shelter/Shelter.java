package com.tailtales.production.shelter;

import com.tailtales.production.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne
    @JoinColumn(name = "shelterAdminId")
    private User shelterAdmin;

//    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Pet> pets = new ArrayList<>();

    public Shelter(Integer shelterId, String name, String location, String contactInfo, String website, String email, String phone, User shelterAdminId) {
        this.shelterId = shelterId;
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
        this.website = website;
        this.email = email;
        this.phone = phone;
        this.shelterAdmin = shelterAdminId;
//        this.pets = pets;
    }
}
