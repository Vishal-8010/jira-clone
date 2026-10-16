package com.vishal.jira_clone.controller;

import com.vishal.jira_clone.entity.User;
import com.vishal.jira_clone.service.UserService;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PatchMapping("/{id}")
    public User editUser(@PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @DeleteMapping
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
