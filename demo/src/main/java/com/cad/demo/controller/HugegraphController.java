package com.cad.demo.controller;

import com.cad.demo.entity.Query;
import com.cad.demo.service.HugegraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
//@RequestMapping("/hugegraph")
public class HugegraphController {

    @Autowired
    private HugegraphService hugegraphService;

//    @PostMapping(value = "/overall_disease")
//    public List<Object> overallDisease(){
//        return hugegraphService.overall();
//    }

    // 全局疾病搜索条目，实现药品附近一跳功能
    @PostMapping(value = "/overall_disease")
    public List<Object> overallDisease(@RequestBody Query query){
        String content = query.getContent();
        return hugegraphService.overallDisease(content);
    }
}

