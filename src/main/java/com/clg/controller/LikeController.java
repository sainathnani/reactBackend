package com.clg.controller;


import com.clg.base.ErrorMessages;
import com.clg.dto.like.LikeRequest;
import com.clg.model.Like;
import com.clg.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/like")
@RestController
public class LikeController {

    @Autowired
    LikeService likeService;


    @PostMapping("/likeProject")
    public ResponseEntity<?> likeProject(@RequestBody LikeRequest like) {

        Like response =  likeService.likeProject(like);
        if(response.getLikeStatus().contains("Failed")) {
            ErrorMessages errorMessages1 = new ErrorMessages(HttpStatus.BAD_REQUEST.value(),"Like Failed");
            return ResponseEntity.badRequest().body(errorMessages1);
        } else if(response.getLikeStatus().contains("Deleted")) {
            return ResponseEntity.ok().body(response);
        }

        return ResponseEntity.ok(response);

    }
}
