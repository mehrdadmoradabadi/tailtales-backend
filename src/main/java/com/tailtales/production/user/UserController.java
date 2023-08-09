package com.tailtales.production.user;

import com.tailtales.production.dto.UpdateUserRequestDto;
import com.tailtales.production.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Delete a user by ID", description = "Delete a user by its Id")
    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteById(@PathVariable Integer userId) {
        userService.deleteById(userId);
        return new ApiResponse<>("200", "successful", "done");
    }

    @Operation(summary = "Find User", description = "Find a user by its Id")
    @GetMapping("/{userId}")
    public ApiResponse<User> getById(@PathVariable Integer userId) {
        return new ApiResponse<>("200", "successful", userService.findById(userId));
    }

    @Operation(summary = "Update User", description = "Update a user by its ID, You need to provide a UpdateUserRequestDto Object too.")
    @PatchMapping("/{userId}")
    public ApiResponse<User> updateById(@PathVariable Integer userId, @RequestBody UpdateUserRequestDto updatedUser) {
        return new ApiResponse<>("200", "successful", userService.updateById(userId, updatedUser));
    }
}
