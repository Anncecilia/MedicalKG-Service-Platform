package com.cad.demo.controller;

import com.baidu.hugegraph.structure.gremlin.Result;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

@CrossOrigin
@RestController
public class RestTestmplateTest {
//    @Autowired
    private RestTemplate restTemplate;

    @Before
    public void init() {
        restTemplate = new RestTemplate();
    }

    @lombok.Data
    static class InnerRes {
        private Status status;
//        private Data vertices;
        private Data result;
    }

    @lombok.Data
    static class Status {
        int code;
        String msg;
    }

    @lombok.Data
    static class Data {
        String vertices;
        long id;
//        String theme;
//        String title;
//        String dynasty;
//        String explain;
//        String content;
//        String author;
    }

//    @GetMapping(value = "/KoutAPI")
    @Test
    public void testGet() {
//        // 使用方法一，不带参数
//        String url = "https://story.hhui.top/detail?id=666106231640";
//        InnerRes res = restTemplate.getForObject(url, InnerRes.class);
//        System.out.println(res);


        // 使用方法一，传参替换
//        String url = "http://114.67.200.39:44640/graphs/hugegraph/traversers/kout?source={?}&max_depth=2";
//        InnerRes res = restTemplate.getForObject(url, InnerRes.class, "383434623822069760");
//        System.out.println(res);
//
//        // 使用方法二，map传参
//        url = "https://story.hhui.top/detail?id={id}";

        //第一次测试所有边的label
        String url0 = "http://114.67.200.39:44640/graphs/hugegraph/schema/edgelabels";
        String s = restTemplate.getForObject(url0, String.class);
        System.out.println(s);

        //第一次测试 Kout 根据起始顶点、方向、边的类型（可选）和深度depth，查找从起始顶点出发恰好depth步可达的顶点
        String url = "http://114.67.200.39:44640/graphs/hugegraph/traversers/kout?source={id}&max_depth=1";
        Map<String, Object> params = new HashMap<>();
//        params.put("id", 383434623822069760L);
//        params.put("id", 383440934471204864L);
//        String s = restTemplate.getForObject(url, String.class, params);
//        System.out.println(s);

//        System.out.println(restTemplate.getForObject(url, String.class, params));
//        InnerRes res = restTemplate.getForObject(url, InnerRes.class, params);
//        ResultSet resultSet = restTemplate.getForObject(url, ResultSet.class, params);
//       System.out.println(resultSet);
//
//        // 使用方法三，URI访问
//        URI uri = URI.create("https://story.hhui.top/detail?id=666106231640");
//        res = restTemplate.getForObject(uri, InnerRes.class);
//        System.out.println(res);

        //第二次测试 Ray 根据起始顶点、方向、边的类型（可选）和最大深度等条件查找发散到边界顶点的路径
//        String url1 = "http://114.67.200.39:44640/graphs/hugegraph/traversers/rays?source={id}&max_depth=2";
//        Map<String, Object> params1 = new HashMap<>();
//        params1.put("id", 383440934471204864L);
//        String s1 = restTemplate.getForObject(url1, String.class, params1);
//        System.out.println(s1);

//        第三次测试 3.2.4 Paths 根据起始顶点、目的顶点、方向、边的类型（可选）和最大深度等条件查找所有路径
//        String url2 = "http://114.67.200.39:44640/graphs/hugegraph/traversers/paths?source={id1}&target={id2}&max_depth=2";
//        Map<String, Object> params2 = new HashMap<>();
//        params2.put("id1", 383440934471204864L);
//        params2.put("id2", 383438059795906560L);
//        String s2 = restTemplate.getForObject(url2, String.class, params2);
//        System.out.println(s2);
        //第四次测试 Edge 2.2.5 获取符合条件的边
//        String url4 = "http://114.67.200.39:44640/graphs/hugegraph/graph/edges?vertex_id={id}";
//        Map<String, Object> params4 = new HashMap<>();
//        params4.put("id", 352169592048582657L);
////        restTemplate = setRestTemplateEncode(restTemplate);
////        String s4 = restTemplate.getForObject(url4, String.class, params4);
//        String s4 = restTemplate.getForObject(url4, String.class, params4);
//        System.out.println(s4);
    }

//    public static RestTemplate setRestTemplateEncode(RestTemplate restTemplate) {
//        if (null == restTemplate || ObjectUtils.isEmpty(restTemplate.getMessageConverters())) {
//            return restTemplate;
//        }
//
//        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
//        for (int i = 0; i < messageConverters.size(); i++) {
//            HttpMessageConverter<?> httpMessageConverter = messageConverters.get(i);
//            if (httpMessageConverter.getClass().equals(StringHttpMessageConverter.class)) {
//                messageConverters.set(i, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//            }
//        }
//        return restTemplate;
//    }
}


