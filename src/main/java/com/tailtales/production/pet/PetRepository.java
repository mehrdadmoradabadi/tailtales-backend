package com.tailtales.production.pet;

import com.tailtales.production.dto.PetDto;
import com.tailtales.production.shelter.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
   Pet findByRegisterNumber(Integer registerNumber);

    List<Pet> findByShelter(Shelter shelter);


}
