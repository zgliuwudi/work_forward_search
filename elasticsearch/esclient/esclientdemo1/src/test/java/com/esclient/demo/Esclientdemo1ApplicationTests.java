package com.esclient.demo;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
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
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class Esclientdemo1ApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("test_1");
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

        System.out.println(response);
    }

    @Test
    void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("test_1");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);

        System.out.println(exists);
    }

    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("test_1");
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);

        System.out.println(response.isAcknowledged());
    }

    @Test
    void testAddDocument() throws IOException {
        IndexRequest request = new IndexRequest("test_1");

        User user = new User();
        user.setName("test1");
        user.setAge(12);
        request.source(JSON.toJSONString(user), XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        System.out.println("id="+response.getId());
    }

    @Test
    void testDataExist() throws IOException {
        GetRequest request = new GetRequest("test_1","_xTFa3QBP_Pne1DeNSyZ");
        boolean exists = client.exists(request, RequestOptions.DEFAULT);

        System.out.println(exists);
    }

    @Test
    void testGetData() throws IOException {
        GetRequest request = new GetRequest("test_1","_xTFa3QBP_Pne1DeNSyZ");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        System.out.println(response.getSourceAsString());
    }

    @Test
    void testUpdateData() throws IOException {
        UpdateRequest request = new UpdateRequest("test_1","_xTFa3QBP_Pne1DeNSyZ");
        User user = new User("newnames", 11);
        request.doc(JSON.toJSONString(user), XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);

        System.out.println(response.status().getStatus());
    }

    @Test
    void testDeleteData() throws IOException {
        DeleteRequest request = new DeleteRequest("test_1","_xTFa3QBP_Pne1DeNSyZ");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);

        System.out.println(response.status().getStatus());
    }

    @Test
    void testBulk() throws IOException {
        BulkRequest request = new BulkRequest();
        for (int i = 0; i < 10; i++) {
            request.add(new IndexRequest("test_1").source(JSON.toJSONString(new User("name"+i,i)),XContentType.JSON));
        }
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);

        System.out.println(response.status().getStatus());
    }

    @Test
    void testSearchData() throws IOException {
        SearchRequest request = new SearchRequest("test_1");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name","name0");
        sourceBuilder.query(termQueryBuilder);
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit : hits1) {
            System.out.println(searchHit.getId()+"==="+searchHit);
        }

        System.out.println(response.status().getStatus());
    }

    @After
    void close() throws IOException {
        client.close();
    }

}
