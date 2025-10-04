package org.iacg.iacgservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "user")
public class User {

    // Getter 和 Setter
    @Id
    private String id;       // MongoDB 自动生成的唯一ID
    private String username; // 用户名
    private String password; // 密码（演示用，明文存储）

}
