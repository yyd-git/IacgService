package org.iacg.iacgservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "acg_product")
public class AcgProduct {
    @Id
    private String id; // MongoDB 自动生成，无需手动赋值

    private String name = "";
    private String type = "";
    private String region = "";
    private String description = "";
    private int bigType = 0;
    private double rating = 0.0;

    // 动漫特有
    private Integer episodes = 0;
    private String director = "";

    // 漫画特有
    private Integer chapters = 0;
    private String author = "";
    private String publisher = "";

    // Galgame特有
    private String developer = "";
    private String platform = "";
}
