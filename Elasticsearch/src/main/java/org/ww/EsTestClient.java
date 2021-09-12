package org.ww;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class EsTestClient
{
    public static void main( String[] args ) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient
                .builder(new HttpHost("192.168.1.4",9200,"http")));

        //创建索引
//        CreateIndexRequest request = new CreateIndexRequest("user");
//        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
//        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
//        System.out.println( "索引操作结果：" + shardsAcknowledged );

        //查询索引
//        GetIndexRequest request = new GetIndexRequest("user");
//        GetIndexResponse getIndexResponse = client.indices().get(request, RequestOptions.DEFAULT);
//        Map<String, List<AliasMetadata>> aliases = getIndexResponse.getAliases();
//        System.out.println( "索引操作结果：" + aliases);

        //删除索引
//        DeleteIndexRequest request = new DeleteIndexRequest("user");
//        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
//        boolean acknowledged = delete.isAcknowledged();
//        System.out.println( "索引操作结果：" + acknowledged);

        client.close();
        System.out.println( "Hello World!" );
    }
}
