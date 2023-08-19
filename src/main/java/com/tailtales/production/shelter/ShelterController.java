package com.tailtales.production.shelter;

import com.tailtales.production.dto.ShelterDto;
import com.tailtales.production.dto.ShelterRequestDto;
import com.tailtales.production.utils.ApiResponse;
import com.tailtales.production.utils.SearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shelters")
public class ShelterController {
    @Autowired
    private ShelterService shelterService;

    @Operation(summary = "Fetch all shelter's", description = "Fetch all shelter's. Optionally you can provide 'page number', 'sortBy' and 'sortDirection'.\n" +
            "Sorting can be done by this parameters: 'name', 'location', 'website', and also search by name or location")
    @GetMapping
    public ApiResponse<SearchResponse<List<ShelterDto>>> fetchAll(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(required = false) String sortBy,
                                                                  @RequestParam(defaultValue = "asc") String sortDirection,
                                                                  @RequestParam(required = false) String search){
        return new ApiResponse<>(shelterService.fetchAll(page, sortBy, sortDirection,search));
    }

    @Operation(summary = "Find a shelter by ID", description = "Find a shelter By shelterId.")
    @GetMapping("/{shelterId}")
    public ApiResponse<Shelter> findById(@PathVariable Integer shelterId){
        return new ApiResponse<>(shelterService.findById(shelterId));
    }

    @Operation(summary = "Update a shelter", description = "Update a shelter by shelterId, You need to provide a ShelterRequestDto too.")
    @PatchMapping("/{shelterId}")
    public ApiResponse<ShelterDto> updateById(@PathVariable Integer shelterId,@RequestBody ShelterRequestDto updatedShelter){
        return new ApiResponse<>(shelterService.update(shelterId,updatedShelter));
    }

}
