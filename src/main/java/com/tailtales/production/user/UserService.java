package com.tailtales.production.user;

import com.tailtales.production.dto.AuthChangePasswordUser;
import com.tailtales.production.dto.UpdateUserRequestDto;
import com.tailtales.production.exceptions.changePassword.ChangePasswordException;
import com.tailtales.production.exceptions.login.Exceptions;
import com.tailtales.production.utils.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User findById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
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
        return new SearchResponse<>(page,totalPages,usersOnPage);
    }

    public User signUp(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new Exceptions.InvalidUserObjectException("Invalid user object. Username and password are required.");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exceptions.UserAlreadyExistsException("User with the provided username already exists.");
        }
        return userRepository.save(user);
    }
    public boolean existByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public void promote(Integer userId, Role role) {
        User user= userRepository.findById(userId).orElse(null);
        assert user != null;
        user.setRole(role);
        userRepository.save(user);
    }

    public void changePassword( AuthChangePasswordUser user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null){
            throw new ChangePasswordException.UserNotFoundException();
        }
        if (!passwordEncoder.matches(user.getOldPassword(), existingUser.getPassword())) {
            throw new ChangePasswordException.IncorrectPasswordException();
        }
        existingUser.setPassword(passwordEncoder.encode(user.getNewPassword()));
        userRepository.save(existingUser);

    }
}
