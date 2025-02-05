package com.tailtales.production.pet;
import com.tailtales.production.dto.PetDto;
import com.tailtales.production.dto.CreatePetRequestDto;
import com.tailtales.production.dto.UpdatePetRequestDto;
import com.tailtales.production.shelter.Shelter;
import com.tailtales.production.shelter.ShelterService;
import com.tailtales.production.utils.SearchResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ShelterService shelterService;
    public PetDto mapToDto(Pet pet){
        PetDto petDto = new PetDto();
        petDto.setId(pet.getPetId());
        petDto.setName(pet.getName());
        petDto.setAge(pet.getAge());
        petDto.setBreed(pet.getBreed());
        petDto.setImages(pet.getImages());
        petDto.setGender(pet.getGender());
        petDto.setSpecies(pet.getSpecies());
        petDto.setDescription(pet.getDescription());
        petDto.setShelterId(pet.getShelter().getShelterId());
        petDto.setColor(pet.getColor());
        petDto.setIsNeuteredSpayed(pet.getIsNeuteredSpayed());
        petDto.setIsVaccinated(pet.getIsVaccinated());
        petDto.setSize(pet.getSize());
        petDto.setRegisterNumber(pet.getRegisterNumber());
        return petDto;
    }
    public SearchResponse<List<PetDto>> fetchAll(int page, String sortBy, String sortDirection, String search, Integer shelterId){
        int pageSize = 10;
        List<Pet> allPets;
        List<PetDto> petDtosList;
        if (search != null && !search.isEmpty()) {
            allPets = petRepository.findByNameContainingIgnoreCaseOrBreedContainingIgnoreCase(search, search);
        } else {
            allPets = petRepository.findAll();
        }
        if(shelterId!=0 ){
            Shelter shelter = shelterService.findById(shelterId);
            allPets = allPets.stream().filter((pet -> pet.getShelter() == shelter)).toList();
        }
        if (sortBy != null) {
            Comparator<Pet> comparator = switch (sortBy.toLowerCase()) {
                case "name" -> Comparator.comparing(Pet::getName);
                case "species" -> Comparator.comparing(Pet::getSpecies);
                case "breed" -> Comparator.comparing(Pet::getBreed);
                case "age" -> Comparator.comparing(Pet::getAge);
                case "gender" -> Comparator.comparing(Pet::getGender);
                default -> throw new IllegalArgumentException("Invalid sortBy parameter: " + sortBy);
            };
            if ("desc".equalsIgnoreCase(sortDirection)) {
                comparator = comparator.reversed();
            }
            allPets.sort(comparator);
        }
        int totalPets = allPets.size();
        int totalPages = (totalPets + pageSize - 1) / pageSize;
        if (page != 0){
        List<Pet> petsOnPage = allPets.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList()
                ;
        petDtosList= petsOnPage.stream().map(this::mapToDto).toList();}
        else { petDtosList=allPets.stream().map(this::mapToDto).toList();}
        return new SearchResponse<>(page, totalPages, petDtosList);
    }

    public PetDto findById(Integer id){
        Pet pet = petRepository.findById(id).orElse(null);
        assert pet != null;
        return mapToDto(pet);
    }
    public PetDto updateById(Integer id, UpdatePetRequestDto updatedPet){
        Shelter shelter= shelterService.findById(updatedPet.getShelterId());
        Pet pet = petRepository.findById(id).orElse(null);
        assert pet != null;
        pet.setAge(updatedPet.getAge());
        pet.setName(updatedPet.getName());
        pet.setImages(updatedPet.getImages());
        pet.setGender(updatedPet.getGender());
        pet.setBreed(updatedPet.getBreed());
        pet.setDescription(updatedPet.getDescription());
        pet.setSpecies(updatedPet.getSpecies());
        pet.setColor(updatedPet.getColor());
        pet.setIsNeuteredSpayed(updatedPet.getIsNeuteredSpayed());
        pet.setIsVaccinated(updatedPet.getIsVaccinated());
        pet.setSize(updatedPet.getSize());
        pet.setShelter(shelter);

        return mapToDto(petRepository.save(pet));
    }

    @Transactional
    public void deleteById(Integer id){
        petRepository.deleteById(id);
    }

    public PetDto add(CreatePetRequestDto newPet) {
        Shelter shelter = shelterService.findById(newPet.getShelterId());
        Pet pet = new Pet();
        pet.setRegisterNumber(newPet.getRegisterNumber());
        pet.setGender(newPet.getGender());
        pet.setSize(newPet.getSize());
        pet.setBreed(newPet.getBreed());
        pet.setShelter(shelter);
        pet.setColor(newPet.getColor());
        pet.setSpecies(newPet.getSpecies());
        pet.setImages(newPet.getImages());
        pet.setIsVaccinated(newPet.getIsVaccinated());
        pet.setIsNeuteredSpayed(newPet.getIsNeuteredSpayed());
        pet.setName(newPet.getName());
        pet.setDescription(newPet.getDescription());
        pet.setAge(newPet.getAge());

        return mapToDto(petRepository.save(pet));
    }

    public Pet findByRegisterId(Integer registerNumber) {
        return petRepository.findByRegisterNumber(registerNumber);
    }


}
