package org.iacg.iacgservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.iacg.iacgservice.model.AcgProduct;
import org.iacg.iacgservice.repository.AcgProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "ACG 产品接口", description = "动漫 / 漫画 / Galgame 统一接口")
public class AcgProductController {

    private final AcgProductRepository repository;

    public AcgProductController(AcgProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "获取所有产品")
    public List<AcgProduct> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据 ID 获取产品")
    public ResponseEntity<AcgProduct> getById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "新增一个产品（动漫/漫画/Galgame），id 自动生成")
    public ResponseEntity<AcgProduct> addProduct(@RequestBody AcgProduct product) {
        // 确保 id 为空，让 MongoDB 自动生成
        product.setId(null);
        AcgProduct saved = repository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新产品信息")
    public ResponseEntity<AcgProduct> updateProduct(@PathVariable String id, @RequestBody AcgProduct product) {
        return repository.findById(id)
                .map(existing -> {
                    product.setId(id);
                    AcgProduct updated = repository.save(product);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除一个产品")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "按名字搜索")
    public List<AcgProduct> searchByName(@RequestParam String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/category/{bigType}")
    @Operation(summary = "按大类型分类浏览")
    public List<AcgProduct> getByCategory(@PathVariable int bigType) {
        return repository.findByBigType(bigType);
    }

}
