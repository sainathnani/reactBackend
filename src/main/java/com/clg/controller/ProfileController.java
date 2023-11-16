package com.clg.controller;

import com.clg.model.Profile;
import com.clg.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@Slf4j
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile newProfile) {

        Profile createdProfile = profileService.createProfile(newProfile);
        return ResponseEntity.ok(createdProfile);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<Profile> getProfile(@PathVariable String username) {

        Profile existingProfile = profileService.getProfile(username);
        log.info("Profile is{}", existingProfile);
        return ResponseEntity.ok(existingProfile);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<Profile> updateProfile(@PathVariable String username, @RequestBody Profile updatedProfile) {
        Profile existingProfile = profileService.getProfile(username);
        if (existingProfile == null) {
            return ResponseEntity.notFound().build();
        }

        log.info("Existing profile is {}",existingProfile);

        existingProfile.setFirstName(updatedProfile.getFirstName());
        existingProfile.setLastName(updatedProfile.getLastName());
        existingProfile.setUserimagePath(updatedProfile.getUserimagePath());
        existingProfile.setDesignation(updatedProfile.getDesignation());
        existingProfile.setAddress(updatedProfile.getAddress());
        existingProfile.setEmail(updatedProfile.getEmail());
        existingProfile.setMobile(updatedProfile.getMobile());
        existingProfile.setSkills(updatedProfile.getSkills());

        Profile updated = profileService.updateProfile(existingProfile);
        return ResponseEntity.ok(updated);
    }

}