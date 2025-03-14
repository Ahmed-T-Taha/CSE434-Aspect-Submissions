package com.example.Lab2.User;

import com.example.Lab2.User.Dto.CreateUserDto;
import com.example.Lab2.User.Dto.UpdateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    public User retrieveUser(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(CreateUserDto userDto) {
        User user = new User(
                userDto.getEmail(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getPhoneNumber()
        );

        return userRepository.save(user);
    }

    public User updateUser(UpdateUserDto userDto) {
        User user = new User(
                userDto.getEmail(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getPhoneNumber()
        );
        user.setId(userDto.getId());

        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }
}
