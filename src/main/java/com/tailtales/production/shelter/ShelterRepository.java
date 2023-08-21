package com.tailtales.production.shelter;

import com.tailtales.production.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Integer> {
    List<Shelter> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String search, String search1);

    Shelter findByShelterAdmin(User admin);

}
