package com.tailtales.production.user;

import com.tailtales.production.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteById(@PathVariable Integer userId) {
        userService.deleteById(userId);
        return new ApiResponse<>("200", "successful", "done");
    }

    @GetMapping("/{userId}")
    public ApiResponse<User> getById(@PathVariable Integer userId) {
        User user = userService.findById(userId);
        return new ApiResponse<>("200", "successful", user);
    }

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        User newUser = userService.add(user);
        return new ApiResponse<>("200", "successful", newUser);
    }

    @PatchMapping("/{userId}")
    public ApiResponse<User> updateById(@PathVariable Integer userId, @RequestBody User updatedUser) {
        User user = userService.updateById(userId, updatedUser);
        return new ApiResponse<>("200", "successful", user);
    }
}
