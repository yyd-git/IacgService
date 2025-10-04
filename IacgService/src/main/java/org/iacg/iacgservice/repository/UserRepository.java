package org.iacg.iacgservice.repository;

import org.iacg.iacgservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // 根据用户名查找用户（登录时会用到）
    User findByUsername(String username);
}