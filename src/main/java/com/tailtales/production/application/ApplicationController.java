package com.tailtales.production.application;

import com.tailtales.production.dto.ApplicationDto;
import com.tailtales.production.dto.ApplicationRequestDto;
import com.tailtales.production.utils.ApiResponse;
import com.tailtales.production.utils.SearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @Operation(summary = "Fetch All", description = "Fetch all application's. Optionally you can provide 'page number', 'sortBy' and 'sortDirection'.\n" +
            "Sorting can be done by this parameters: 'date', 'status'")
    @GetMapping
    public ApiResponse<SearchResponse<List<ApplicationDto>>> fetchAll(@RequestParam(defaultValue = "1") int page,
                                                                      @RequestParam(required = false) String sortBy,
                                                                      @RequestParam(defaultValue = "asc") String sortDirection){
        return new ApiResponse<>(applicationService.fetchAll(page,sortBy,sortDirection));
    }
    @Operation(summary = "Find By ID", description = "Find application by its ID")
    @GetMapping("/{applicationId}")
    public ApiResponse<ApplicationDto> fetchById(@PathVariable Integer applicationId){
        return new ApiResponse<>(applicationService.fetchById(applicationId));
    }

    @Operation(summary = "Create", description = "Create a new application, You need to provide ApplicationRequestDto too")
    @PostMapping(consumes = {"application/json"})
    public ApiResponse<ApplicationDto> add(@RequestBody ApplicationRequestDto newApplication){
        return new ApiResponse<>(applicationService.add(newApplication));
    }

    @Operation(summary = "Update By ID", description = "Update application by its ID")
    @PatchMapping(path = "/{applicationId}",consumes ={"application/json"} )
    public ApiResponse<ApplicationDto> update(@PathVariable Integer applicationId, @RequestBody ApplicationRequestDto updatedApplication){
        return new ApiResponse<>(applicationService.update(applicationId,updatedApplication));
    }

    @Operation(summary = "Delete By ID", description = "Delete application by its ID")
    @DeleteMapping("/{applicationId}")
    public ApiResponse<String> delete(@PathVariable Integer applicationId){
        applicationService.delete(applicationId);
        return new ApiResponse<>("done");
    }
}
