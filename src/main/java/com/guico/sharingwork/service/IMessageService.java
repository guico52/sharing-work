package com.guico.sharingwork.service;

import cn.hutool.json.JSONObject;
import org.bson.json.JsonObject;

public interface IMessageService {
    void process(String gridKey, JSONObject message);
}
