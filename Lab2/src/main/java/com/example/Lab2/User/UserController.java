package com.example.Lab2.User;

import com.example.Lab2.User.Dto.CreateUserDto;
import com.example.Lab2.User.Dto.UpdateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> retrieveAllUsers() {
        return userService.retrieveAllUsers();
    }

    @GetMapping("/{id}")
    public User retrieveUser(@PathVariable Integer id) {
        return userService.retrieveUser(id);
    }

    @PostMapping
    public User createUser(@RequestBody CreateUserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping
    public User updateUser(@RequestBody UpdateUserDto userDto) {
        return userService.updateUser(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
