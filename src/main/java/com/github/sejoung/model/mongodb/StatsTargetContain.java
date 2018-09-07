package com.github.sejoung.model.mongodb;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Document
public class StatsTargetContain {

    @Id
    private String id;
    
    @Field(value="PC")
    private long pc;

    @Field(value="MOBILE")
    private long mobile;

    @Field(value="UNKNOWN")
    private long unknown;

    @Field(value="TOTAL")
    private long total;
    
    private String targetType;
    private String targetData;
    private String collDate;
    private Date regDate;

    
}
