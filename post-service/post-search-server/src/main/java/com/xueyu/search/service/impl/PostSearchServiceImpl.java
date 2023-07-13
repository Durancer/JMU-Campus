package com.xueyu.search.service.impl;

import org.apache.commons.lang3.StringUtils;
import com.xueyu.search.exception.PostSearchException;
import com.alibaba.fastjson.JSON;
import com.xueyu.search.pojo.UserSearchVO;
import com.xueyu.search.service.PostSearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PostSearchServiceImpl implements PostSearchService {

    @Resource
    private RestHighLevelClient restHighLevelClient;


    @Override
    public List<Map<String,String>> searchByPage(UserSearchVO userSearchVO) {
        //1.检查参数
        if(userSearchVO == null || StringUtils.isBlank(userSearchVO.getSearchWords())){
            throw new PostSearchException("搜索内容为空");
        }

        //2.设置查询请求
        SearchRequest searchRequest = new SearchRequest("jmu_post_info");
        //查询条件构造
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //bool查询构建
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //分词之后再进行查询
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(userSearchVO.getSearchWords()).field("title").field("content").defaultOperator(Operator.OR);
        boolQueryBuilder.must(queryStringQueryBuilder);

        //分页
        searchSourceBuilder.from(userSearchVO.getPage());
        searchSourceBuilder.size(userSearchVO.getPageSize());

        //按照发布时间倒序查询
        searchSourceBuilder.sort("createTime", SortOrder.DESC);

        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.preTags("<font style='color: red; font-size: inherit;'>");
        highlightBuilder.postTags("</font>");
        searchSourceBuilder.highlighter(highlightBuilder);

        //条件查询设置
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //查询结果解析
        SearchHit[] hits = searchResponse.getHits().getHits();
        //查询结果封装
        List<Map<String,String>> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            Map<String,String> map = JSON.parseObject(json, Map.class);
            //处理高亮
            if(hit.getHighlightFields() != null && hit.getHighlightFields().size() > 0){
                Text[] titles = hit.getHighlightFields().get("title").getFragments();
                String title = StringUtils.join(titles);
                //高亮标题
                map.put("h_title",title);
            }else {
                //原始标题
                map.put("h_title",map.get("title"));
            }
            list.add(map);
        }
        return list;
    }
}
