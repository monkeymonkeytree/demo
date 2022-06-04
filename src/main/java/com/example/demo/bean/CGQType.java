package com.example.demo.bean;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@ToString
@Document("cGQType")
@AllArgsConstructor
@NoArgsConstructor
public class CGQType {
    private Integer id;
    private String name;
    private String type;
    private String updateTime;
    private String status;


}
