package com.guico.sharingwork.controller;

import cn.hutool.json.JSONUtil;
import com.guico.sharingwork.common.Result;
import com.guico.sharingwork.entity.WorkBookEntity;
import com.guico.sharingwork.entity.WorkSheetEntity;
import com.guico.sharingwork.repository.WorkBookRepository;
import com.guico.sharingwork.repository.WorkSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    WorkBookRepository workBookRepository;

    WorkSheetRepository workSheetRepository;

    @Autowired
    public void setWorkBookRepository(WorkBookRepository workBookRepository) {
        this.workBookRepository = workBookRepository;
    }

    @Autowired
    public void setWorkSheetRepository(WorkSheetRepository workSheetRepository) {
        this.workSheetRepository = workSheetRepository;
    }

    @GetMapping("/loadUrl")
    Result loadUrl(String id, String username){
        WorkBookEntity data = workBookRepository.findById(id).isPresent() ? workBookRepository.findById(id).get() : null;
        if (data == null){
            return null;
        }
        return Result.success(id, username, JSONUtil.toJsonStr(data));
    }

    @GetMapping("/loadSheetUrl")
    Result loadSheetUrl(String id, String username){
        WorkSheetEntity data = workSheetRepository.findById(id).isPresent() ? workSheetRepository.findById(id).get() : null;
        if (data == null){
            return null;
        }
        return Result.success(id, username, JSONUtil.toJsonStr(data));
    }
}
