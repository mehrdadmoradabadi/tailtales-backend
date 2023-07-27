package com.tailtales.production.admin;

import com.tailtales.production.dto.ShelterDto;
import com.tailtales.production.shelter.Shelter;
import com.tailtales.production.shelter.ShelterService;
import com.tailtales.production.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/shelter")
public class AdminShelterController {
    @Autowired
    private ShelterService shelterService;

    @PostMapping( consumes = {"application/json"})
    public ApiResponse<ShelterDto> addShelter(@RequestBody Shelter newShelter){
        return new ApiResponse<>("200","Successful",shelterService.add(newShelter));
    }
}
