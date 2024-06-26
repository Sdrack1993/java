package com.sirack.fullstactbankend.controller;

import com.sirack.fullstactbankend.exception.UserNotFoundException;
import com.sirack.fullstactbankend.model.User;
import com.sirack.fullstactbankend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

// writing data to the database API
    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);

    }

// getting all users API

    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return userRepository.findById(id).map(user ->{
            user.setUserName(newUser.getUserName());
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());

            return userRepository.save(user);
        }).orElseThrow(()->new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id "+id+" has been deleted successfully.";
    }

}
