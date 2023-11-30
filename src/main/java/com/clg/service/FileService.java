package com.clg.service;


import com.clg.model.Files;
import com.clg.model.Profile;
import com.clg.repository.FileRepository;
import com.clg.repository.ProfileRepository;
import com.clg.sequence.SequenceGeneratorService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileService {


    @Autowired
    private FileRepository fileRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    public Long addResume(String userName, MultipartFile incomingFile, String fileType) throws IOException {
        Files files;
        if(null != userName) {
            files = fileRepository.findFilesByUserNameAndFileType(userName,fileType);

            if(null != files){
                files.setFile(
                        new Binary(BsonBinarySubType.BINARY, incomingFile.getBytes()));
                files = fileRepository.save(files);
                return files.getId();
            }
        }


        files = new Files();
        files.setUserName(userName);
        files.setId(sequenceGeneratorService.generateSequence(Files.SEQUENCE_NAME));
        files.setFile(
                new Binary(BsonBinarySubType.BINARY, incomingFile.getBytes()));
        files.setFileType(fileType);
        files.setOriginalFileName(incomingFile.getOriginalFilename());
        files.setContentType(incomingFile.getContentType());
        files = fileRepository.insert(files);
        Profile profile =  profileRepository.findByUsername(userName);

        if(null != profile){
            profile.setResumeId(files.getId());
            profileRepository.save(profile);
        }
        return files.getId();
    }

    public Files downloadFile(Long id) {
        return fileRepository.findById(id).get();
    }
}
