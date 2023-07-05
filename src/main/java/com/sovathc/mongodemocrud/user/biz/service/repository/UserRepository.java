package com.sovathc.mongodemocrud.user.biz.service.repository;

import com.sovathc.mongodemocrud.user.biz.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    List<UserEntity> findAllByUsername(String username);
}
