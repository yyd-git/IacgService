package org.iacg.iacgservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.iacg.iacgservice.model.Comment;
import org.iacg.iacgservice.repository.CommentRepository;
import org.iacg.iacgservice.repository.AcgProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Tag(name = "评论接口", description = "对动漫/漫画/Galgame的评论相关操作")
public class CommentController {

    private final CommentRepository commentRepository;
    private final AcgProductRepository productRepository;

    public CommentController(CommentRepository commentRepository, AcgProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    @Operation(summary = "新增评论")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        Comment saved = commentRepository.save(comment);
        updateProductRating(comment.getTargetId()); // 新增后更新评分
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "获取某个产品的所有评论")
    public List<Comment> getCommentsByProduct(@PathVariable String productId) {
        return commentRepository.findByTargetId(productId);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取某个用户的所有评论")
    public List<Comment> getCommentsByUser(@PathVariable String userId) {
        return commentRepository.findByUserId(userId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论")
    public ResponseEntity<Object> deleteComment(@PathVariable String id) {
        return commentRepository.findById(id)
                .map(comment -> {
                    String productId = comment.getTargetId();
                    commentRepository.deleteById(id);
                    updateProductRating(productId); // 删除后更新评分
                    return ResponseEntity.<Void>noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/product/{productId}/rating")
    @Operation(summary = "获取某个产品的平均评分")
    public ResponseEntity<Double> getAverageRating(@PathVariable String productId) {
        List<Comment> comments = commentRepository.findByTargetId(productId);
        if (comments.isEmpty()) {
            return ResponseEntity.ok(0.0);
        }
        double avg = comments.stream()
                .mapToDouble(Comment::getRating)
                .average()
                .orElse(0.0);
        return ResponseEntity.ok(avg);
    }

    /** 计算并更新产品的平均评分 */
    private void updateProductRating(String productId) {
        List<Comment> comments = commentRepository.findByTargetId(productId);
        double avg = comments.stream()
                .mapToDouble(Comment::getRating)
                .average()
                .orElse(0.0);

        productRepository.findById(productId).ifPresent(product -> {
            product.setRating(avg);
            productRepository.save(product);
        });
    }
}
