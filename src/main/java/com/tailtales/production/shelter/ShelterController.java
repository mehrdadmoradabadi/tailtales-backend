package com.tailtales.production.shelter;

import com.tailtales.production.dto.PetDto;
import com.tailtales.production.dto.ShelterDto;
import com.tailtales.production.pet.Pet;
import com.tailtales.production.utils.ApiResponse;
import com.tailtales.production.utils.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
public class ShelterController {
    @Autowired
    private ShelterService shelterService;

    @GetMapping
    public ApiResponse<SearchResponse<List<ShelterDto>>> fetchAll(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(required = false) String sortBy,
                                                                  @RequestParam(defaultValue = "asc") String sortDirection){
        return new ApiResponse<>("200","Successful",shelterService.fetchAll(page, sortBy, sortDirection));
    }

    @GetMapping("/{shelterId")
    public ApiResponse<ShelterDto> findById(@PathVariable Integer shelterId){
        return new ApiResponse<>("200","Successful",shelterService.findById(shelterId));
    }

    @PatchMapping("/{shelterId")
    public ApiResponse<Shelter> updateById(@PathVariable Integer shelterId,@RequestBody Shelter updatedShelter){
        return new ApiResponse<>("200","Successful",shelterService.update(shelterId,updatedShelter));
    }

    @GetMapping("/{shelterId}/pets")
    public ApiResponse<SearchResponse<List<PetDto>>> fetchPets(@PathVariable Integer shelterId,
                                                            @RequestParam(defaultValue = "1") int page,
                                                            @RequestParam(required = false) String sortBy,
                                                            @RequestParam(defaultValue = "asc") String sortDirection){
        return new ApiResponse<>("200","Successful",shelterService.getPets(shelterId,page,sortBy,sortDirection));
    }

    @PostMapping(consumes = {"application/json"})
    public ApiResponse<PetDto> addPet(@PathVariable Integer shelterId, @RequestBody Pet newPet){
        return new ApiResponse<>("200","Successful",shelterService.addPet(shelterId,newPet));
    }

    @PatchMapping(path = "/{shelterId}/{petId}",consumes ={"application/json"} )
    public ApiResponse<PetDto> updatePet(@PathVariable Integer shelterId, @RequestBody Pet updatedPet){
        return new ApiResponse<>("200","Successful",shelterService.updatePet(shelterId,updatedPet));
    }

    @DeleteMapping(path = "/{shelterId}/{petId}")
    public ApiResponse<String> deletePet(@PathVariable Integer shelterId, @PathVariable Integer petId){
        shelterService.deletePet(shelterId,petId);
        return new ApiResponse<>("200","Successful","done");
    }
}
