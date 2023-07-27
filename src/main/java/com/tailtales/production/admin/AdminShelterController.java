package com.tailtales.production.admin;

import com.tailtales.production.dto.ShelterDto;
import com.tailtales.production.shelter.Shelter;
import com.tailtales.production.shelter.ShelterService;
import com.tailtales.production.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/shelter")
public class AdminShelterController {
    @Autowired
    private ShelterService shelterService;

    @PostMapping( consumes = {"application/json"})
    public ApiResponse<ShelterDto> add(@RequestBody Shelter newShelter){
        return new ApiResponse<>("200","Successful",shelterService.add(newShelter));
    }

    @DeleteMapping("/{shelterId}")
    public ApiResponse<String> delete(@PathVariable Integer shelterId){
        shelterService.delete(shelterId);
        return new ApiResponse<>("200","Successful","done");
    }
}
