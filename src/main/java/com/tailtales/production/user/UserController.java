package com.tailtales.production.user;

import com.tailtales.production.dto.AuthChangePasswordUser;
import com.tailtales.production.dto.UpdateUserRequestDto;
import com.tailtales.production.exceptions.changePassword.ChangePasswordException;
import com.tailtales.production.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Delete a user by ID", description = "Delete a user by its Id")
    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteById(@PathVariable Integer userId) {
        userService.deleteById(userId);
        return new ApiResponse<>("done");
    }

    @Operation(summary = "Find User", description = "Find a user by its Id")
    @GetMapping("/{userId}")
    public ApiResponse<User> getById(@PathVariable Integer userId) {
        return new ApiResponse<>( userService.findById(userId));
    }

    @Operation(summary = "Update User", description = "Update a user by its ID, You need to provide a UpdateUserRequestDto Object too.")
    @PatchMapping("/{userId}")
    public ApiResponse<User> updateById(@PathVariable Integer userId, @RequestBody UpdateUserRequestDto updatedUser) {
        return new ApiResponse<>(userService.updateById(userId, updatedUser));
    }
    @Operation(summary = "Change Password", description = "Update a users password by its Username, You need to provide a AuthChangePasswordUser Object too.")
    @PatchMapping(value = "/change-password",consumes = {"application/json"})
    public ResponseEntity<?> changePassword(@RequestBody AuthChangePasswordUser user){
        try{
        userService.changePassword(user);
        return ResponseEntity.ok(new ApiResponse<>("successful"));
        }catch (ChangePasswordException.UserNotFoundException  ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch (ChangePasswordException.IncorrectPasswordException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
