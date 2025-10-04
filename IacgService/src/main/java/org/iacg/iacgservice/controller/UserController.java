package org.iacg.iacgservice.controller;

import org.iacg.iacgservice.model.User;
import org.iacg.iacgservice.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User 接口", description = "用户相关的增删改查 API")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @Operation(summary = "获取所有用户")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取用户")
    public User getUserById(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping
    @Operation(summary = "新增用户")
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户信息")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录（简单示例，明文密码匹配）")
    public String login(@RequestBody User loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return "登录成功！欢迎 " + user.getUsername();
        } else {
            return "登录失败：用户名或密码错误";
        }
    }

}
