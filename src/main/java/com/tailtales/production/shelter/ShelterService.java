package com.tailtales.production.shelter;

import com.tailtales.production.dto.PetDto;
import com.tailtales.production.dto.ShelterDto;
import com.tailtales.production.pet.Pet;
import com.tailtales.production.pet.PetService;
import com.tailtales.production.utils.SearchResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShelterService {
    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private PetService petService;

    public ShelterDto mapToDto(Shelter shelter){
        ShelterDto shelterDto = new ShelterDto();
        shelterDto.setName(shelter.getName());
        shelterDto.setEmail(shelter.getEmail());
        shelterDto.setPets(shelter.getPets());
        shelterDto.setPhone(shelter.getPhone());
        shelterDto.setLocation(shelter.getLocation());
        shelterDto.setWebsite(shelter.getWebsite());
        shelterDto.setContactInfo(shelter.getContactInfo());
        return shelterDto;
    }

    public SearchResponse<List<ShelterDto>>fetchAll(int page, String sortBy, String sortDirection){
        int pageSize = 10;
        List<Shelter> allShelters = shelterRepository.findAll();
        if (sortBy != null) {
            Comparator<Shelter> comparator = switch (sortBy.toLowerCase()) {
                case "name" -> Comparator.comparing(Shelter::getName);
                case "location" -> Comparator.comparing(Shelter::getLocation);
                default -> throw new IllegalArgumentException("Invalid sortBy parameter: " + sortBy);
            };
            if ("desc".equalsIgnoreCase(sortDirection)) {
                comparator = comparator.reversed();
            }
            allShelters.sort(comparator);
        }
        int totalShelters = allShelters.size();
        int totalPages = (totalShelters + pageSize - 1) / pageSize;
        List<Shelter> sheltersOnPage = allShelters.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList()
                ;
        List<ShelterDto> shelterDtosList= sheltersOnPage.stream().map(this::mapToDto).collect(Collectors.toList());
        return new SearchResponse<List<ShelterDto>>(page,totalShelters,shelterDtosList);
    }

    public ShelterDto findById(Integer id){
        Shelter shelter = shelterRepository.findById(id).orElse(null);
        assert shelter !=null;
        return mapToDto(shelter);
    }

    public Shelter update(Integer id, Shelter updatedShelter){
        Shelter existingShelter = shelterRepository.findById(id).orElse(null);
        assert existingShelter != null;
        existingShelter.setWebsite(updatedShelter.getWebsite());
        existingShelter.setPhone(updatedShelter.getPhone());
        existingShelter.setContactInfo(updatedShelter.getContactInfo());
        existingShelter.setLocation(updatedShelter.getLocation());
        return shelterRepository.save(existingShelter);

    }

    public PetDto mapPetToDto(Pet pet){
        PetDto petDto = new PetDto();
        petDto.setName(pet.getName());
        petDto.setAge(pet.getAge());
        petDto.setBreed(pet.getBreed());
        petDto.setImages(pet.getImages());
        petDto.setGender(pet.getGender());
        petDto.setSpecies(pet.getSpecies());
        petDto.setDescription(pet.getDescription());
        return petDto;
    }
    public SearchResponse<List<PetDto>> getPets(Integer shelterId,int page, String sortBy, String sortDirection) {
        int pageSize = 10;
        List<Pet> allPets = shelterRepository.findAllPets();
        if (sortBy != null) {
            Comparator<Pet> comparator = switch (sortBy.toLowerCase()) {
                case "name" -> Comparator.comparing(Pet::getName);
                case "location" -> Comparator.comparing(Pet::getGender);
                default -> throw new IllegalArgumentException("Invalid sortBy parameter: " + sortBy);
            };
            if ("desc".equalsIgnoreCase(sortDirection)) {
                comparator = comparator.reversed();
            }
            allPets.sort(comparator);
        }
        int totalPets = allPets.size();
        int totalPages = (totalPets + pageSize - 1) / pageSize;
        List<Pet> petsOnPage = allPets.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList()
                ;
        List<PetDto> petDtosList= petsOnPage.stream().map(this::mapPetToDto).collect(Collectors.toList());
        return new SearchResponse<List<PetDto>>(page,totalPets,petDtosList);
    }

    public PetDto addPet(Integer shelterId, Pet newPet) {
        Shelter shelter = shelterRepository.findById(shelterId).orElseThrow(() -> new EntityNotFoundException("Shelter not found"));
        List<Pet> allPets = shelter.getPets();
        boolean petAlreadyExists = allPets.stream().anyMatch(pet -> pet.getRegisterNumber().equals(newPet.getRegisterNumber()));
//        Pet petAlreadyExistsAtAll= petService.findByRegisterId(newPet.getRegisterNumber());
        if (!petAlreadyExists) {
            newPet.setShelter(shelter);
            petService.add(newPet);
            allPets.add(petService.add(newPet));
        }
        shelter.setPets(allPets);
        shelterRepository.save(shelter);

        return mapPetToDto(newPet);
    }

    public PetDto updatePet(Integer shelterId, Pet updatedPet) {
        Shelter shelter = shelterRepository.findById(shelterId).orElse(null);
        assert shelter != null;
        List<Pet> allPets = shelter.getPets();
        for (Pet pet:allPets){
            if (pet.getPetId().equals(updatedPet.getPetId())){
                pet.setName(updatedPet.getName());
                pet.setAge(updatedPet.getAge());
                pet.setSpecies(updatedPet.getSpecies());
                pet.setImages(updatedPet.getImages());
                pet.setGender(updatedPet.getGender());
                pet.setBreed(updatedPet.getBreed());
                pet.setDescription(updatedPet.getDescription());
                pet.setColor(updatedPet.getColor());
                pet.setGender(updatedPet.getGender());
                pet.setIsNeuteredSpayed(updatedPet.getIsNeuteredSpayed());
                pet.setIsVaccinated(updatedPet.getIsVaccinated());
                pet.setSize(updatedPet.getSize());
                shelterRepository.save(shelter);
                return mapPetToDto(pet);
                }
        }
        return null;

    }

    public void deletePet(Integer shelterId, Integer petId) {
        Shelter shelter= shelterRepository.findById(shelterId).orElse(null);
        assert shelter != null;
        List<Pet> pets = shelter.getPets();
        pets.stream().filter(pet -> pet.getPetId().equals(petId)).findFirst().ifPresent(pets::remove);
        shelterRepository.save(shelter);
    }

    public ShelterDto add(Shelter newShelter) {
        return mapToDto(shelterRepository.save(newShelter));
    }
}
