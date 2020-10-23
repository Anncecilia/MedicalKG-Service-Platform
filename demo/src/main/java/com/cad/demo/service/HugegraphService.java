package com.cad.demo.service;

import com.baidu.hugegraph.structure.graph.Edge;
import com.baidu.hugegraph.structure.graph.Vertex;
import com.baidu.hugegraph.structure.gremlin.Result;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import com.cad.demo.dao.HugegraphDAO;
import com.cad.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
@Service
public class HugegraphService {


    @Autowired
    private HugegraphDAO hugegraphDao;
    public List<Object> overall(){
        List<Object> result = new ArrayList<>();
        int length = hugegraphDao.All().size();
        for(int i=0;i<length;i++){
            result.add(hugegraphDao.All().get(i));
        }
//        result.addAll(makeListObjects(hugegraphDao.All()));
        return result;
    }

    private List<Object> makeListObjects(ResultSet resultSet) {
        Iterator<Result> results = resultSet.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            someList.add(object);
        });
        return someList;
    }

    public List<Object> overallDisease(String content){
        List<Object> result = new ArrayList<>();
        result.addAll(queryList("disease", content));
        result.addAll(makeListObjects(hugegraphDao.drugNeighbourDisease(content)));
        return result;
    }

    public List<Object> queryList(String category, String content){
        ResultSet resultSet = null;
        if(category.equals("disease")) {
            /*
            单疾病用药查询
             */
            resultSet = hugegraphDao.searchDiseaseList(content);
        }

        return makeListObjects(resultSet);
    }
}
