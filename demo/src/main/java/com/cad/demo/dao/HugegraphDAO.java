package com.cad.demo.dao;

import com.baidu.hugegraph.driver.GremlinManager;
import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import org.springframework.stereotype.Repository;

@Repository
public class HugegraphDAO {
    HugeClient hugeClient = new HugeClient("http://114.67.200.39:44640","hugegraph");
    GremlinManager gremlin = hugeClient.gremlin();
    public ResultSet All() {
        return gremlin.gremlin("g.V().limit(10).label()").execute();
    }

    public ResultSet drugNeighbourDisease(String content) {
        return gremlin.gremlin("g.V().and(filter(values('name').is(Text.contains('" + content + "')))," +
                "filter(label().is(Text.contains('药品')))).both().filter(label().is(Text.contains('疾病')))").execute();
    }
    public ResultSet searchDiseaseList(String content){
        return gremlin.gremlin("g.V().hasLabel('疾病','疾病类型').filter(values('name')" +
                ".is(Text.contains('"+content+"'))).properties('name')").execute();
    }
}
