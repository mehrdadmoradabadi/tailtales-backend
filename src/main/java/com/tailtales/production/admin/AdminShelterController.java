package com.tailtales.production.admin;

import com.tailtales.production.dto.ShelterDto;
import com.tailtales.production.dto.ShelterRequestDto;
import com.tailtales.production.shelter.ShelterService;
import com.tailtales.production.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/shelter")
@PreAuthorize("hasRole('ADMIN')")
public class AdminShelterController {
    @Autowired
    private ShelterService shelterService;

    @Operation(summary = "Add new shelter ", description = "Adds new Shelter to database you need to send a shelterRequestDto.")
    @PostMapping( consumes = {"application/json"})
    public ApiResponse<ShelterDto> add(@RequestBody ShelterRequestDto newShelter){
        return new ApiResponse<>("200","Successful",shelterService.add(newShelter));
    }
    @Operation(summary = "Delete shelter by ID", description = "Delete a shelter by Id and returns Done.")
    @DeleteMapping("/{shelterId}")
    public ApiResponse<String> delete(@PathVariable Integer shelterId){
        shelterService.delete(shelterId);
        return new ApiResponse<>("200","Successful","done");
    }
}
