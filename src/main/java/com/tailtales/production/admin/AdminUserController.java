package com.tailtales.production.admin;

import com.tailtales.production.user.User;
import com.tailtales.production.user.UserService;
import com.tailtales.production.utils.ApiResponse;
import com.tailtales.production.utils.SearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Fetch All Users ", description = "To retrieve all users from database.")

    @GetMapping
    public ApiResponse<SearchResponse<List<User>>> fetchAll(@RequestParam(defaultValue = "1") int page) {

        String message = "Successfully fetched users on page " + page;
        return new ApiResponse<>("200", message, userService.findAll(page));
    }
}
