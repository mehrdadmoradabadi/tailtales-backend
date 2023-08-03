package com.tailtales.production.application;

import com.tailtales.production.dto.ApplicationDto;
import com.tailtales.production.dto.ApplicationRequestDto;
import com.tailtales.production.pet.Pet;
import com.tailtales.production.pet.PetService;
import com.tailtales.production.shelter.Shelter;
import com.tailtales.production.shelter.ShelterService;
import com.tailtales.production.user.User;
import com.tailtales.production.user.UserService;
import com.tailtales.production.utils.SearchResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PetService petService;
    @Autowired
    private ShelterService shelterService;
    public ApplicationDto mapToDto(@NotNull Application application){
        ApplicationDto applicationDto = new ApplicationDto();
        applicationDto.setComments(application.getComments());
        applicationDto.setDate(application.getDate());
        applicationDto.setShelterId(application.getShelter().getShelterId());
        applicationDto.setPetId(application.getPet().getPetId());
        applicationDto.setStatus(application.getStatus());
        applicationDto.setOutcomeDate(application.getOutcomeDate());
        applicationDto.setUsername(application.getUser().getUsername());
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
    public ApplicationDto update(Integer id, @NotNull ApplicationRequestDto updatedApplication){
        Pet pet= petService.findByRegisterId(updatedApplication.getPetRegisterId());
        User user = userService.findById(updatedApplication.getUserId());
        Application existingApplication = applicationRepository.findById(id).orElse(null);
        assert existingApplication !=null;
        existingApplication.setComments(updatedApplication.getComments());
        existingApplication.setPet(pet);
        existingApplication.setStatus(updatedApplication.getStatus());
        existingApplication.setOutcomeDate(updatedApplication.getOutcomeDate());
        existingApplication.setUser(user);
        applicationRepository.save(existingApplication);
    return mapToDto(existingApplication);
    }

    public ApplicationDto add(@NotNull ApplicationRequestDto newApplication){
        User user = userService.findById(newApplication.getUserId());
        Shelter shelter = shelterService.findById(newApplication.getShelterId());
        Pet pet = petService.findByRegisterId(newApplication.getPetRegisterId());
        LocalDateTime time = LocalDateTime.now();
        Application application = new Application(user,shelter,pet,newApplication.getStatus(),time,newApplication.getComments(),newApplication.getOutcomeDate());

        return mapToDto(applicationRepository.save(application));
    }
    public void delete(Integer applicationId){
        applicationRepository.deleteById(applicationId);
    }
}

