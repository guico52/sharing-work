package com.guico.sharingwork.entity;

import cn.hutool.json.JSONObject;
import lombok.Data;
import org.bson.json.JsonObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "workbook")
@Data
public class WorkBookEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String name;

    private JSONObject option;
}
