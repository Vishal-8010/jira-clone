package com.vishal.jira_clone.service;

import com.vishal.jira_clone.entity.User;
import com.vishal.jira_clone.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User editUser(Long id, User user){
        User oldUser = userRepository.findById(id).orElse(null);
        if(oldUser == null) return null;
        if(user.getUserName() != null) oldUser.setUserName(user.getUserName());
        if(user.getEmail() != null) oldUser.setEmail(user.getEmail());
        if(user.getPassword() != null) oldUser.setEmail(user.getPassword());
        return userRepository.save(oldUser);
    }

    public User updateUser(Long id, User user){
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser == null) return null;
        existingUser.setEmail(user.getEmail());
        existingUser.setUserName(user.getUserName());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

}
