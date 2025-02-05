package com.tailtales.production.pet;

import com.tailtales.production.dto.CreatePetRequestDto;
import com.tailtales.production.dto.PetDto;
import com.tailtales.production.dto.UpdatePetRequestDto;
import com.tailtales.production.utils.ApiResponse;
import com.tailtales.production.utils.SearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {
    @Autowired
    private PetService petService;

    @Operation(summary = "Fetch all pets ", description = "Fetch all pets . Optionally you can provide 'page number', 'sortBy' and 'sortDirection'.\n" +
            "Sorting can be done by this parameters: 'name', 'age', 'breed', 'species', 'gender' also search is available by name or breed")
    @GetMapping("/")
    public ApiResponse<SearchResponse<List<PetDto>>> fetchAll(@RequestParam(defaultValue = "0") Integer page,
                                                              @RequestParam(required = false) String sortBy,
                                                              @RequestParam(defaultValue = "asc") String sortDirection,
                                                              @RequestParam(required = false) String search,
                                                              @RequestParam(required = false,defaultValue = "0") Integer shelterId){

        return new ApiResponse<>(petService.fetchAll(page,sortBy,sortDirection,search,shelterId));
    }

    @Operation(summary = "Add a new Pet", description = "Add a new Pet to Database. you need to send CreatePetRequestDto Object.")
    @PostMapping(consumes = {"application/json"},path = "/")
    public ApiResponse<PetDto> add(@RequestBody CreatePetRequestDto pet){
        return new ApiResponse<>(petService.add(pet));
    }
    @Operation(summary = "Find a pet by its Id", description = "Find a pet by It's ID")
    @GetMapping(path = "/pet/{petId}")
    public ApiResponse<PetDto> findById(@PathVariable Integer petId){

        return new ApiResponse<>(petService.findById(petId));
    }
    @Operation(summary = "Update a pet by ID", description = "Update a pet by Id, You need to provide a UpdatePetRequestDto for updated Pet too")
    @PatchMapping("/update/{petId}")
    public ApiResponse<PetDto> updateById(@PathVariable Integer petId,@RequestBody UpdatePetRequestDto updatedPet){
        return new ApiResponse<>(petService.updateById(petId,updatedPet));
    }
    @Operation(summary = "Delete a pet by ID", description = "Delete a pet by ID from database")
    @DeleteMapping("/{petId}")
    public ApiResponse<String> deleteById(@PathVariable Integer petId){
        petService.deleteById(petId);
        return new ApiResponse<>("done");
    }
}
