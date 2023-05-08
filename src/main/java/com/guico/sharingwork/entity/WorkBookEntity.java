package com.guico.sharingwork.entity;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.json.JsonObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "workbook")
@Data
public class WorkBookEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    // 表格的id
    @Id
    private String id;

    // 表格的名字
    private String name;

    // 表格的配置项
    private JSONObject option;

}


