package com.tailtales.production.application;

import com.tailtales.production.dto.ApplicationDto;
import com.tailtales.production.dto.ShelterDto;
import com.tailtales.production.shelter.Shelter;
import com.tailtales.production.utils.SearchResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    public ApplicationDto mapToDto(@NotNull Application application){
        ApplicationDto applicationDto = new ApplicationDto();
        applicationDto.setComments(application.getComments());
        applicationDto.setDate(application.getDate());
        applicationDto.setShelter(application.getShelter());
        applicationDto.setPet(application.getPet());
        applicationDto.setStatus(application.getStatus());
        applicationDto.setOutcomeDate(application.getOutcomeDate());
        return applicationDto;
    }
    public SearchResponse<List<ApplicationDto>> fetchAll(int page, String sortBy, String sortDirection){
        int pageSize = 10;
        List<Application> allApplication = applicationRepository.findAll();
        if (sortBy != null) {
            Comparator<Application> comparator = switch (sortBy.toLowerCase()) {
                case "date" -> Comparator.comparing(Application:: getDate);
                case "status" -> Comparator.comparing(Application::getStatus);
                default -> throw new IllegalArgumentException("Invalid sortBy parameter: " + sortBy);
            };
            if ("desc".equalsIgnoreCase(sortDirection)) {
                comparator = comparator.reversed();
            }
            allApplication.sort(comparator);
        }
        int totalApplication = allApplication.size();
        int totalPages = (totalApplication + pageSize - 1) / pageSize;
        List<Application> applicationOnPage = allApplication.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList()
                ;
        List<ApplicationDto> applicationDtosList= applicationOnPage.stream().map(this::mapToDto).collect(Collectors.toList());
        return new SearchResponse<List<ApplicationDto>>(page,totalApplication,applicationDtosList);
    }

    public ApplicationDto fetchById(Integer applicationId){
        Application application = applicationRepository.findById(applicationId).orElse(null);
        assert application != null;
        return mapToDto(application);
    }
    public ApplicationDto update(Integer id, @NotNull Application updatedApplication){
        Application existingApplication = applicationRepository.findById(id).orElse(null);
        assert existingApplication !=null;
        existingApplication.setComments(updatedApplication.getComments());
        existingApplication.setPet(updatedApplication.getPet());
        existingApplication.setStatus(updatedApplication.getStatus());
        existingApplication.setOutcomeDate(updatedApplication.getOutcomeDate());
        existingApplication.setUser(updatedApplication.getUser());
        applicationRepository.save(existingApplication);
    return mapToDto(existingApplication);
    }

    public ApplicationDto add(@NotNull Application application){
        return mapToDto(applicationRepository.save(application));
    }
    public void delete(Integer applicationId){
        applicationRepository.deleteById(applicationId);
    }
}

