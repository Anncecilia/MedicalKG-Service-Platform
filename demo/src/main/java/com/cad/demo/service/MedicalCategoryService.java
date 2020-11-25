package com.cad.demo.service;

import com.cad.demo.pojo.MedicalCategory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

@Service
public class MedicalCategoryService {
    private MongoTemplate mongoTemplate;

    public MedicalCategoryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<MedicalCategory> getMedicalCategory(){
        List<MedicalCategory> res;
        Query query = new Query();
        res = mongoTemplate.find(query, MedicalCategory.class);
        return res;
    }
}
