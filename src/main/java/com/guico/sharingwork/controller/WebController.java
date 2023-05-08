package com.guico.sharingwork.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.guico.sharingwork.common.Result;
import com.guico.sharingwork.entity.WorkBookEntity;
import com.guico.sharingwork.entity.WorkSheetEntity;
import com.guico.sharingwork.repository.WorkBookRepository;
import com.guico.sharingwork.repository.WorkSheetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@Slf4j
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

    @RequestMapping("/add")
    Boolean addBook(String gridKey, String name){
        if(StrUtil.isBlank(name)) {
            name = "default";
        }
        WorkBookEntity wb = new WorkBookEntity();
        wb.setName(name);
        wb.setOption(null);

        WorkBookEntity saveWb = workBookRepository.save(wb);
        return true;
    }

    @RequestMapping("/loadUrl")
    Object loadUrl(String id){
        List<WorkSheetEntity> sheets = workSheetRepository.findAllBywbId(id);
        // 获取data的所有data属性，并重组为一个List
        List<JSONObject> data = sheets.stream().map(WorkSheetEntity::getData).collect(Collectors.toList());
        return  data.toString();
    }

    @RequestMapping("/loadSheetUrl")
    Object loadSheetUrl(String id){
        WorkSheetEntity data = workSheetRepository.findById(id).isPresent() ? workSheetRepository.findById(id).get() : null;
        if (data == null){
            return null;
        }
        return data.getData();
    }

    @RequestMapping("/all")
    Object allWorkBook(){
        return workBookRepository.findAll();
    }
}
