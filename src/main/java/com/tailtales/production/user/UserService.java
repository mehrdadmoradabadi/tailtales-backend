package com.tailtales.production.user;

import com.tailtales.production.dto.UpdateUserRequestDto;
import com.tailtales.production.dto.UserDto;
import com.tailtales.production.dto.CreateUserRequestDto;
import com.tailtales.production.utils.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDto mapToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(user.getRole());
        userDto.setUsername(user.getUsername());
        userDto.setProfilePicture(user.getProfilePicture());
        return userDto;
    }
    public User findById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User add(CreateUserRequestDto newUser) {
        User user = new User();
        user.setLastName(newUser.getLastName());
        user.setFirstName(newUser.getFirstName());
        user.setPassword(newUser.getPassword());
        user.setProfilePicture(newUser.getProfilePicture());
        user.setUsername(newUser.getUsername());
        return userRepository.save(user);
    }

    public User updateById(Integer userId, UpdateUserRequestDto updatedUser) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser == null) {
            return null;
        }
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setProfilePicture(updatedUser.getProfilePicture());
        return userRepository.save(existingUser);
    }

    public void deleteById(Integer userId) {
        userRepository.deleteById(userId);

    }

    public SearchResponse<List<User>> findAll(int page) {
        List<User> allUsers = userRepository.findAll();
        int pageSize = 10;
        int totalUsers = allUsers.size();
        int totalPages = (totalUsers + pageSize - 1) / pageSize;
        List<User> usersOnPage = allUsers.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList();
        return new SearchResponse<List<User>>(page,totalPages,usersOnPage);
    }

    public User signUp(User user) {
        return userRepository.save(user);
    }
}
