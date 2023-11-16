package com.clg.repository;

import com.clg.model.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserInfoRepository extends MongoRepository<UserInfo, Integer> {
    Optional<UserInfo>  findByEmail(String username);
}
