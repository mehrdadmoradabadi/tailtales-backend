package com.tailtales.production.pet;

import com.tailtales.production.dto.PetDto;
import com.tailtales.production.utils.ApiResponse;
import com.tailtales.production.utils.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {
    @Autowired
    private PetService petService;
    @GetMapping
    public ApiResponse<SearchResponse<List<PetDto>>> fetchAll(@RequestParam(defaultValue = "1") int page,
                                                              @RequestParam(required = false) String sortBy,
                                                              @RequestParam(defaultValue = "asc") String sortDirection){
        String message = "Successful";
        return new ApiResponse<>("200",message,petService.fetchAll(page,sortBy,sortDirection));
    }

    @PostMapping(consumes = {"application/json"})
    public ApiResponse<Pet> add(@RequestBody Pet pet){
        return new ApiResponse<>("200","Successful",petService.add(pet));
    }
    @GetMapping("/{petId}")
    public ApiResponse<PetDto> findById(@RequestParam Integer petId){
        return new ApiResponse<>("200","Successful",petService.findById(petId));
    }
    @PatchMapping("/{petId}")
    public ApiResponse<Pet> updateById(@PathVariable Integer petId,@RequestBody Pet updatedPet){
        return new ApiResponse<>("200","Successful",petService.updateById(petId,updatedPet));
    }
    @DeleteMapping("/{petId}")
    public ApiResponse<String> deleteById(@PathVariable Integer petId){
        petService.deleteById(petId);
        return new ApiResponse<>("200","Successful","done");
    }
}
