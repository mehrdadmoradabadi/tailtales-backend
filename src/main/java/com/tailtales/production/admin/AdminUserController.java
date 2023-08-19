package com.tailtales.production.admin;

import com.tailtales.production.user.Role;
import com.tailtales.production.user.User;
import com.tailtales.production.user.UserService;
import com.tailtales.production.utils.ApiResponse;
import com.tailtales.production.utils.SearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/user")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Fetch All Users ", description = "To retrieve all users from database.")

    @GetMapping
    public ApiResponse<SearchResponse<List<User>>> fetchAll(@RequestParam(defaultValue = "0") int page ,@RequestParam(required = false) String search) {

        String message = "Successfully fetched users on page " + page;
        return new ApiResponse<>("200", message, userService.findAll(page,search));
    }
    @Operation(summary = "Promote a user ", description = "To Change a users role. send a Role")

    @PatchMapping(consumes = {"application/json"},path = "/{userId}")
    public ApiResponse<String> promote(@PathVariable Integer userId, @RequestBody Role role){
        userService.promote(userId,role);
        return new ApiResponse<>("Successfully");
    }
}
