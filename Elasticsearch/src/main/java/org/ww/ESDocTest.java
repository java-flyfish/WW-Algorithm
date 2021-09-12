package org.ww;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.Arrays;

public class ESDocTest {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient
                .builder(new HttpHost("192.168.1.4",9200,"http")));

        //插入数据
        /*IndexRequest request = new IndexRequest();
        request.index("user").id("10001");
        User user = new User();
        user.setName("张三");
        user.setSex("女");
        user.setAge(10);
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);

        request.source(userJson, XContentType.JSON);
        IndexResponse index = client.index(request, RequestOptions.DEFAULT);
        System.out.println( "索引操作结果：" + index);*/

        
        //更新数据
        /*UpdateRequest request = new UpdateRequest();
        request.index("user").id("10001");
        request.doc(XContentType.JSON,"sex","男");

        UpdateResponse update = client.update(request, RequestOptions.DEFAULT);
        System.out.println( "索引操作结果：" + update);*/

        //查询数据
        /*GetRequest request = new GetRequest();
        request.index("user").id("10001");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println( "索引操作结果：" + response.getSourceAsString());*/

        //删除数据
        /*DeleteRequest request = new DeleteRequest();
        request.index("user").id("10001");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println( "索引操作结果：" + response);*/

        //批量新增
        /*BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("user").id("100001").source(XContentType.JSON,"name","张三","sex","男","age",10));
        request.add(new IndexRequest().index("user").id("100002").source(XContentType.JSON,"name","李四","sex","男","age",20));
        request.add(new IndexRequest().index("user").id("100003").source(XContentType.JSON,"name","王武","sex","男","age",25));
        request.add(new IndexRequest().index("user").id("100004").source(XContentType.JSON,"name","赵六","sex","女","age",21));
        request.add(new IndexRequest().index("user").id("100005").source(XContentType.JSON,"name","孙七","sex","男","age",10));
        request.add(new IndexRequest().index("user").id("100006").source(XContentType.JSON,"name","孙七","sex","男","age",30));
        request.add(new IndexRequest().index("user").id("100007").source(XContentType.JSON,"name","周八","sex","男","age",52));
        request.add(new IndexRequest().index("user").id("100008").source(XContentType.JSON,"name","吴九","sex","男","age",43));
        request.add(new IndexRequest().index("user").id("100009").source(XContentType.JSON,"name","郑十","sex","女","age",22));
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println( "索引操作结果：" + response.getTook());
        System.out.println( "索引操作结果：" + response.getItems());*/


        //批量删除
        /*BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("user").id("100001"));
        request.add(new DeleteRequest().index("user").id("100002"));
        request.add(new DeleteRequest().index("user").id("100003"));
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println( "索引操作结果：" + response.getTook());
        System.out.println( "索引操作结果：" + response.getItems());*/

        //高级查询
        /*SearchRequest request = new SearchRequest();
        request.indices("user");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //QueryBuilders.matchAllQuery()查询所有
        //QueryBuilders.matchQuery("name","张三")查询指定属性
        //QueryBuilders.termQuery("age",30)条件查询(完全匹配)
        builder.query(QueryBuilders.matchAllQuery());
        //分页、排序
        builder.from(0).size(2).sort("age", SortOrder.ASC);

        //只查询某个字段
        String[] includes = {"name"};
        String[] excludes = {};
        builder.fetchSource(includes,excludes);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println( "命中结果：" + Arrays.toString(hits.getHits()));
        System.out.println( "花费时间：" + response.getTook());
        client.close();*/

        //组合查询
        /*SearchRequest request = new SearchRequest();
        request.indices("user");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //QueryBuilders.matchAllQuery()查询所有
        //QueryBuilders.matchQuery("name","张三")查询指定属性
        //QueryBuilders.termQuery("age",30)条件查询(完全匹配)
        //QueryBuilders.boolQuery()组合查询条件
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        boolQuery.must(QueryBuilders.matchQuery("name","张三"));
        boolQuery.must(QueryBuilders.matchQuery("sex","男"));
        //或者条件查询
//        boolQuery.should(QueryBuilders.matchQuery("age",30));
//        boolQuery.should(QueryBuilders.matchQuery("age",50));
        boolQuery.filter(QueryBuilders.rangeQuery("age").lte(60).gt(30));
        builder.query(boolQuery);
        //分页、排序
        builder.from(0).size(2).sort("age", SortOrder.ASC);

        //只查询某个字段
        String[] includes = {"name"};
        String[] excludes = {};
        builder.fetchSource(includes,excludes);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println( "命中结果：" + Arrays.toString(hits.getHits()));
        System.out.println( "花费时间：" + response.getTook());
        client.close();*/

        //模糊查询
        /*SearchRequest request = new SearchRequest();
        request.indices("user");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //QueryBuilders.matchAllQuery()查询所有
        //QueryBuilders.matchQuery("name","张三")查询指定属性
        //QueryBuilders.termQuery("age",30)条件查询(完全匹配)
        //QueryBuilders.boolQuery()组合查询条件
        //QueryBuilders.fuzzyQuery("name","王")模糊查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        boolQuery.must(QueryBuilders.matchQuery("name","张三"));
        //.fuzziness(Fuzziness.ONE)表示差一个字符能匹配成功
        boolQuery.must(QueryBuilders.fuzzyQuery("name","王花").fuzziness(Fuzziness.TWO));
        builder.query(boolQuery);

        //高亮查询
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");
        builder.highlighter(highlightBuilder);
        //只查询某个字段
        String[] includes = {"name"};
        String[] excludes = {};
        builder.fetchSource(includes,excludes);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println( "命中结果：" + Arrays.toString(hits.getHits()));
        System.out.println( "花费时间：" + response.getTook());
        client.close();*/




        //聚合查询
        SearchRequest request = new SearchRequest();
        request.indices("user");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //.max("maxAge").field("age")最大值查询
        //.terms("ageGroup").field("age")分组查询
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
        builder.aggregation(aggregationBuilder);
        //只查询某个字段
        String[] includes = {"name"};
        String[] excludes = {};
        builder.fetchSource(includes,excludes);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println( "命中结果：" + response);
        System.out.println( "花费时间：" + response.getTook());
        client.close();
    }
}
