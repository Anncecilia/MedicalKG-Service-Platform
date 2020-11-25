package com.cad.demo.dao;

import com.baidu.hugegraph.driver.GremlinManager;
import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.driver.SchemaManager;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import org.springframework.stereotype.Repository;

@Repository
public class HugegraphDAO {
    HugeClient hugeClient = new HugeClient("http://114.67.200.39:44640","hugegraph");
    SchemaManager schema = hugeClient.schema();
    GremlinManager gremlin = hugeClient.gremlin();

    //返回所有实体
    public ResultSet getAllClass(){
        return gremlin.gremlin("g.V().limit(500)").execute();
    }
    //返回所有标签
    public ResultSet getAllLabel() {return gremlin.gremlin("g.V().label()").execute();}
    //通过指定边的入顶点找到出顶点
//    public ResultSet getEdgeNode(String inV){return gremlin.gremlin("g.E().outV().hasLabel('"+inV+"')").execute();}
    public ResultSet getEdgeNode(String inV){return gremlin.gremlin("g.V().hasLabel('"+inV+"').inE().outV().path()").execute();}
    //获取所有边
//    public ResultSet getAllEdge() {return gremlin.gremlin("g.E()").execute();}

    public ResultSet getNameById(String id){
        return gremlin.gremlin("g.V().hasId("+ id +").values('name')").execute();
    }

    public ResultSet FromLabeltoFindV(String category){
//        System.out.println("g.V().hasLabel('"+category+"')");
        return gremlin.gremlin("g.V().hasLabel('"+category+"').limit(500)").execute();
    }

    public ResultSet IdfindEdge(String id){
        System.out.println("g.V("+id+").outE()");
        return gremlin.gremlin("g.V("+id+").outE()").execute();
    }



}
