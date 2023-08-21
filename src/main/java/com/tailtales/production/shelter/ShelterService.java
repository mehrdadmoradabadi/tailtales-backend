package com.tailtales.production.shelter;

import com.tailtales.production.dto.ShelterDto;
import com.tailtales.production.dto.ShelterRequestDto;
import com.tailtales.production.user.User;
import com.tailtales.production.user.UserService;
import com.tailtales.production.utils.SearchResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShelterService {
    @Autowired
    private ShelterRepository shelterRepository;
    @Autowired
    private UserService userService;
    public ShelterDto mapToDto(Shelter shelter){
        ShelterDto shelterDto = new ShelterDto();
        shelterDto.setShelterId(shelter.getShelterId());
        shelterDto.setName(shelter.getName());
        shelterDto.setEmail(shelter.getEmail());
        shelterDto.setPhone(shelter.getPhone());
        shelterDto.setLocation(shelter.getLocation());
        shelterDto.setWebsite(shelter.getWebsite());
        shelterDto.setContactInfo(shelter.getContactInfo());
        shelterDto.setShelterAdminId(shelter.getShelterAdmin().getUserId());
        return shelterDto;
    }

    public SearchResponse<List<ShelterDto>>fetchAll(int page, String sortBy, String sortDirection,String search){
        int pageSize = 10;
        List<Shelter> allShelters;
        List<ShelterDto> shelterDtosList;
        long totalShelters ;
        if (search != null && !search.isEmpty()) {
            // Search for pets by name or breed
            allShelters = shelterRepository.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(search, search);
            totalShelters = allShelters.size();
        } else {
            allShelters = shelterRepository.findAll();
            totalShelters = shelterRepository.count();
        }

        if (sortBy != null) {
            Comparator<Shelter> comparator = switch (sortBy.toLowerCase()) {
                case "name" -> Comparator.comparing(Shelter::getName);
                case "location" -> Comparator.comparing(Shelter::getLocation);
                case "website" -> Comparator.comparing(Shelter::getWebsite);
                default -> throw new IllegalArgumentException("Invalid sortBy parameter: " + sortBy);
            };
            if ("desc".equalsIgnoreCase(sortDirection)) {
                comparator = comparator.reversed();
            }
            allShelters.sort(comparator);
        }

        long totalPages = (totalShelters + pageSize - 1) / pageSize;
        if(page != 0){
        List<Shelter> sheltersOnPage = allShelters.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList()
                ;
        shelterDtosList= sheltersOnPage.stream().map(this::mapToDto).collect(Collectors.toList());}
        else { shelterDtosList= allShelters.stream().map(this::mapToDto).collect(Collectors.toList());}
        return new SearchResponse<>(page, totalPages, shelterDtosList);
    }

    public Shelter findById(Integer id){
        Shelter shelter = shelterRepository.findById(id).orElse(null);
        assert shelter !=null;
        return shelter;
    }

    public ShelterDto update(Integer id, @NotNull ShelterRequestDto updatedShelter){
        Shelter existingShelter = shelterRepository.findById(id).orElse(null);
        assert existingShelter != null;
        existingShelter.setWebsite(updatedShelter.getWebsite());
        existingShelter.setPhone(updatedShelter.getPhone());
        existingShelter.setContactInfo(updatedShelter.getContactInfo());
        existingShelter.setLocation(updatedShelter.getLocation());
        return mapToDto(shelterRepository.save(existingShelter));

    }


    public ShelterDto add(ShelterRequestDto newShelter) {
        User shelterAdmin = userService.findById(newShelter.getShelterAdminId());
        Shelter shelter = new Shelter();
        shelter.setPhone(newShelter.getPhone());
        shelter.setName(newShelter.getName());
        shelter.setShelterAdmin(shelterAdmin);
        shelter.setLocation(newShelter.getLocation());
        shelter.setEmail(newShelter.getEmail());
        shelter.setWebsite(newShelter.getWebsite());
        shelter.setContactInfo(newShelter.getContactInfo());


        return mapToDto(shelterRepository.save(shelter));
    }

    public void delete(Integer shelterId) {
        shelterRepository.deleteById(shelterId);
    }


    public ShelterDto findByAdminId(Integer adminId) {
        User admin = userService.findById(adminId);
        return mapToDto(shelterRepository.findByShelterAdmin(admin));
    }
}
