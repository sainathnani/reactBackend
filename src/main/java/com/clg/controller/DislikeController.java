package com.clg.controller;


import com.clg.base.ErrorMessages;
import com.clg.dto.dislike.DislikeRequest;
import com.clg.dto.dislike.DislikeResponse;
import com.clg.dto.like.LikeRequest;
import com.clg.model.Dislike;
import com.clg.model.Like;
import com.clg.service.DislikeService;
import com.clg.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/dislike")
@RestController
public class DislikeController {

    @Autowired
    DislikeService dislikeService;


    @PostMapping("/dislikeProject")
    public ResponseEntity<?> dislikeProject(@RequestBody DislikeRequest dislikeRequest) {

        DislikeResponse response =  dislikeService.dislikeProject(dislikeRequest);
        if(response.getDislikeStatus().contains("Failed")) {
            ErrorMessages errorMessages1 = new ErrorMessages(HttpStatus.BAD_REQUEST.value(),"Dislike Failed");
            return ResponseEntity.badRequest().body(errorMessages1);
        } else if(response.getDislikeStatus().contains("Deleted")) {
            return ResponseEntity.ok().body(response);
        }

        return ResponseEntity.ok(response);

    }
}
