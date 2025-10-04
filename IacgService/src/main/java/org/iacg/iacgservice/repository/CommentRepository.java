package org.iacg.iacgservice.repository;

import org.iacg.iacgservice.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByTargetId(String targetId);
    List<Comment> findByUserId(String userId);
}
