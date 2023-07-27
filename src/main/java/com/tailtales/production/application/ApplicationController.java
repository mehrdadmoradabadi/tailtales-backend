package com.tailtales.production.application;

import com.tailtales.production.dto.ApplicationDto;
import com.tailtales.production.utils.ApiResponse;
import com.tailtales.production.utils.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public ApiResponse<SearchResponse<List<ApplicationDto>>> fetchAll(@RequestParam(defaultValue = "1") int page,
                                                                      @RequestParam(required = false) String sortBy,
                                                                      @RequestParam(defaultValue = "asc") String sortDirection){
        return new ApiResponse<>(applicationService.fetchAll(page,sortBy,sortDirection));
    }
    @GetMapping("/{applicationId}")
    public ApiResponse<ApplicationDto> fetchById(@PathVariable Integer applicationId){
        return new ApiResponse<>(applicationService.fetchById(applicationId));
    }

    @PostMapping(consumes = {"application/json"})
    public ApiResponse<ApplicationDto> add(@RequestBody Application newApplication){
        return new ApiResponse<>(applicationService.add(newApplication));
    }

    @PatchMapping(path = "/{applicationId}",consumes ={"application/json"} )
    public ApiResponse<ApplicationDto> update(@PathVariable Integer applicationId, @RequestBody Application updatedApplication){
        return new ApiResponse<>(applicationService.update(applicationId,updatedApplication));
    }

    @DeleteMapping("/{applicationId}")
    public ApiResponse<String> delete(@PathVariable Integer applicationId){
        applicationService.delete(applicationId);
        return new ApiResponse<>("done");
    }
}
