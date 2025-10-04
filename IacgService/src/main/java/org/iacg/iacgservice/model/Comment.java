package org.iacg.iacgservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "comment")
public class Comment {
    @Id
    private String id; // MongoDB 自动生成

    private String userId;    // 用户 ID（User._id）
    private String targetId;  // 评论目标 ID（acg_product 的 id）
    private String content;   // 评论内容
    private Double rating;    // 评分（允许小数，比如 4.5）

    @CreatedDate
    private LocalDateTime createdAt; // 评论时间（自动生成）
}
