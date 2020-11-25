package com.cad.demo.controller;

import com.cad.demo.pojo.MedicalCategory;
import com.cad.demo.service.MedicalCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/MedicalCategory")
public class MedicalCategoryController {
    @Autowired
    private MedicalCategoryService medicalCategoryService;
    // 获取最新文献列表 或 搜索文献列表
    @PostMapping(value = "/get")
    public List<MedicalCategory> getReference(){
        List<MedicalCategory> res= medicalCategoryService.getMedicalCategory();
//        System.out.println(res);
        return res;
    }
}
