package com.guico.sharingwork.entity;

import cn.hutool.json.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Document(collection = "worksheet")
@Getter
@Setter
public class WorkSheetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    // 表格的id
    @Id
    private String id;

    // 表格所属的WorkBook的id
    private String wbId;

    // 表格的配置项
    private JSONObject data;

    private int deleteStatus;
}
