package com.clg.service;

import com.clg.model.Profile;
import com.clg.repository.ProfileRepository;
import com.clg.sequence.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    public Profile createProfile(Profile profileData) {
        profileData.setId(sequenceGeneratorService.generateSequence(Profile.SEQUENCE_NAME));
        return profileRepository.save(profileData);
    }

    public Profile getProfile(String username) {
        log.info("Fetching Profile");
        return profileRepository.findByUsername(username);
    }

    public Profile updateProfile(Profile updatedProfile) {
        return profileRepository.save(updatedProfile);
    }

}


