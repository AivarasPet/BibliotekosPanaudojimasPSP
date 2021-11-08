package com.PSP.Services;

import com.PSP.Models.User;
import com.PSP.Repository.UserRepository;
import com.PSP.Validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public User saveUser(User user) {
        if(userValidator.validateEmail(user) == false) {
            throw new IllegalArgumentException("Invalid email");
        }
        if(userValidator.validatePhoneNumber(user) == false) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        if(userValidator.validatePassword(user) == false) {
            throw new IllegalArgumentException("Invalid password");
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
