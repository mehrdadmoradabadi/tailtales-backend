package com.tailtales.production.pet;

import com.tailtales.production.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    Optional<Pet> findByRegisterId(Integer registerNumber);
}
