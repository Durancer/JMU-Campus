package com.xueyu.search.listener;

import com.alibaba.fastjson.JSONObject;
import com.xueyu.post.sdk.dto.PostDTO;
import com.xueyu.post.sdk.dto.PostOperateDTO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.IOException;

import static com.xueyu.post.sdk.constant.PostMqContants.*;

/**
 * 帖子搜索服务mq监听
 * 服务队列命名准则：
 * 服务 . 消息发送服务 . 操作行为
 *
 * @author fsj0591
 */
@Component
@Slf4j
public class PostMqListener {

    public static final String POST_QUEUE = "post.search.post.add";

    public static final String POST_DELETE_QUEUE = "post.search.post.delete";

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 添加文章到es
     *
     * @param postDTO 帖子dto
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = POST_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = POST_QUEUE),
            key = POST_PASS_KEY
    ))
    public void addPostToES(PostDTO postDTO) {
        if(postDTO!=null){
            log.info("SyncArticleListener,message={}",postDTO);

            IndexRequest indexRequest = new IndexRequest("jmu_post_info");
            indexRequest.id(postDTO.getId().toString());
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(postDTO);
            indexRequest.source(jsonObject, XContentType.JSON);
            try {
                restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("sync es error={}",e);
            }
        }
    }


    /**
     * es删除帖子
     *
     * @param postOperateDTO post操作dto
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = POST_EXCHANGE, type = ExchangeTypes.TOPIC),
            value = @Queue(name = POST_DELETE_QUEUE),
            key = POST_DELETE_KEY
    ))
    public void deletePostToES(PostOperateDTO postOperateDTO) {
        if(postOperateDTO!=null){
            log.info("es删除帖子数据",postOperateDTO);

            DeleteRequest deleteRequest = new DeleteRequest("jmu_post_info");
            deleteRequest.id(postOperateDTO.getPostId().toString());
            try {
                restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("es删除异常",e);
            }
        }
    }

}
