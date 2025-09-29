package com.scm.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entity.User;
import com.scm.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String id= UUID.randomUUID().toString();
        user.setUserId(id);
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(User user) {
        return Optional.of(userRepo.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user.getUserId())));
    }

    @Override
    public Optional<User> updateUser(User user) {
        User updateUser = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user.getUserId()));

        // Update the fields
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setAbout(user.getAbout());
        updateUser.setPhoneNumber(user.getPhoneNumber());
        updateUser.setProfilePic(user.getProfilePic());
        updateUser.setPassword(user.getPassword());
        updateUser.setEnabled(user.isEnabled());
        updateUser.setEmailVerified(user.isEmailVerified());
        updateUser.setPhoneVerified(user.isPhoneVerified());
        updateUser.setProvider(user.getProvider());
        updateUser.setProviderUserId(user.getProviderUserId());
        
        User savedUser = userRepo.save(updateUser);
        return Optional.ofNullable(savedUser);
    }

    //It should be like this

    //  @Override
    // public Optional<User> updateUser(User userDetails) {
    //     User existingUser = userRepo.findById(userDetails.getUserId())
    //         .orElseThrow(() -> new RuntimeException("User not found with id: " + userDetails.getUserId()));

    //     // **CRITICAL: Only update SAFE fields**
    //     existingUser.setName(userDetails.getName());
    //     existingUser.setEmail(userDetails.getEmail()); 
    //     existingUser.setAbout(userDetails.getAbout());
    //     existingUser.setPhoneNumber(userDetails.getPhoneNumber());
    //     existingUser.setProfilePic(userDetails.getProfilePic());
        
    //     // **DO NOT update password, enabled status, or provider fields here**
    //    User updatedUser = userRepo.save(existingUser);
    //    return Optional.ofNullable(updatedUser);

    // }

    @Override
    public void deleteUser(String userId) {
       User deletUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        userRepo.delete(deletUser);
    }

    @Override
    public boolean isUserExist(String userId) {
        User findUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return findUser != null? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
      User userEmail= userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return userEmail!=null? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

}
