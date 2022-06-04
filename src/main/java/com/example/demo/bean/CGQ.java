package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.repository.ExistsQuery;

import java.io.Serializable;

@Document("cGQ")
@NoArgsConstructor
@AllArgsConstructor
@Data
public  class CGQ implements Serializable {
    @Field("_id")
    private org.bson.types.ObjectId id;
    private String name;
    private String updateTime;
    private String value;
}
