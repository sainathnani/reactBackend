package com.clg.service;

import com.clg.dto.flag.*;
import com.clg.model.Comments;
import com.clg.model.Flag;
import com.clg.model.UserInfo;
import com.clg.repository.CommentRepository;
import com.clg.repository.FlagRepository;
import com.clg.repository.UserInfoRepository;
import com.clg.sequence.SequenceGeneratorService;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCursor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlagService {
    @Autowired
    FlagRepository flagRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    public FlagResponse flagComment(FlagRequest flagRequest) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Flag flagEntity = new Flag();

        flagEntity.setFlagId(sequenceGeneratorService.generateSequence(Flag.SEQUENCE_NAME));

        flagEntity.setFlaggedByUser(username);
        flagEntity.setFlaggedUser(flagRequest.getFlaggedUser());
        flagEntity.setStatus("Pending");
        flagEntity.setCommentId(flagRequest.getCommentId());
        flagEntity = flagRepository.save(flagEntity);

        FlagResponse flagResponse = new FlagResponse();
        BeanUtils.copyProperties(flagEntity, flagResponse);

        return flagResponse;
    }

    public FlaggedUserResponse getFlaggedUsers() {

        FlaggedUserResponse userResponse = new FlaggedUserResponse();

        DistinctIterable<String> distinctIterable = mongoTemplate.getCollection("flag").distinct("flaggedUser", String.class);

        Set<String> users = new HashSet<>();

        for (String user : distinctIterable) {
            users.add(user);
        }

        List<Flag> allFlags = flagRepository.findFlagByStatus("Pending");

        users = allFlags.stream().map(Flag::getFlaggedUser).collect(Collectors.toSet());

        userResponse.setUsers(users);

        return userResponse;

    }

    public List<Comments> getFlaggedUserData(String emailId) {

        List<Comments> commentsList = new ArrayList<>();
        if (null != emailId) {

            List<Flag> flagData = flagRepository.findFlagByFlaggedUserAndStatus(emailId, "Pending");

            for (Flag flag : flagData) {
                if (null != flag.getCommentId()) {
                    Optional<Comments> comment = commentRepository.findById(flag.getCommentId());
                    comment.ifPresent(commentsList::add);
                }
            }

        }
        return commentsList;
    }

    public FlagStatusResponse updateFlagStatus(FlagStatusRequest flagStatusRequest) {
        List<Flag> allFlags;
        String status = flagStatusRequest.getStatus();
        String emailId = flagStatusRequest.getEmailId();
        if (null != emailId) {
            allFlags = flagRepository.findFlagByFlaggedUserAndStatus(emailId,"Pending");
            for (Flag flag : allFlags) {
                flag.setStatus(status);
            }
            if (!allFlags.isEmpty()) {
                flagRepository.saveAll(allFlags);
            }

            if (status.equalsIgnoreCase("Approved")) {
                UserInfo userInfo = userInfoRepository.findByEmail(flagStatusRequest.getEmailId()).get();
                userInfo.setStatus(false);
                userInfoRepository.save(userInfo);
                return FlagStatusResponse.builder().status("Approved").emailId(emailId).build();
            }
        }

        return FlagStatusResponse.builder().status("Rejected").emailId(emailId).build();
    }
}


