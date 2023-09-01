package com.zsy.service.search;

import com.zsy.constatns.PageConstants;
import com.zsy.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author 郑书宇
 * @create 2023/8/30 23:29
 * @desc
 */
@Service
public class MedilsearchClient {
    @Value("${meilisearch.apiHost}")
    private String apiHost;
    @Value("${meilisearch.apiKey}")
    private String apiKey;
    @Resource
    private RestTemplate restTemplate;

    private HttpHeaders httpHeaders;

    public void init(){
        httpHeaders=new HttpHeaders();
        httpHeaders.add("Content-Type","application/json;charset=utf-8");
        httpHeaders.add("Authorization","Bearer " + apiKey);
    }

    public void createIndex(String index){
        String requestBody = "{ \"uid\": \"" + index + "\", \"primaryKey\": \"id\" }";
        String url = apiHost+"/indexes/" + index;
        ResponseEntity<Object> result = restTemplate.postForEntity(url, new HttpEntity(requestBody,httpHeaders), Object.class);
        if(result.getStatusCodeValue()!=200){
            throw new CustomException(30000,"创建搜索INDEX失败");
        }
    }

    public void dropIndex(String index){
        String url = apiHost + "/indexes/" + index;
        restTemplate.exchange(url, HttpMethod.DELETE,new HttpEntity(null,httpHeaders), String.class);
    }

    public void deleteAllDocument(String index){
        String url = apiHost +  "/indexes/" + index + "/documents";
        restTemplate.exchange(url, HttpMethod.DELETE,new HttpEntity(null,httpHeaders), String.class);
    }

    public void saveDocuments(String index,Object documents){
        String url = apiHost+"/indexes/" + index + "/documents";
        restTemplate.exchange(url, HttpMethod.POST,new HttpEntity(documents,httpHeaders), String.class);
    }

    public SearchResponse search(String index,String keyword,int page){
        SearchRequest request = SearchRequest.builder().q(keyword).offset((page - 1) * PageConstants.SEARCH_BLOG_LIST_COUNT)
                .attributesToHighlight(Arrays.asList("*")).limit(PageConstants.SEARCH_BLOG_LIST_COUNT)
                .showMatchesPosition(false).highlightPreTag("<strong>").highlightPostTag("</strong>").build();
        String url = apiHost + "/indexes/" + index + "/search";
        ResponseEntity<SearchResponse> result = restTemplate.exchange(url, HttpMethod.POST,new HttpEntity(request,httpHeaders), SearchResponse.class);
        if(result.getStatusCodeValue()!=200){
            throw new CustomException(30000,"搜索出现错误");
        }
        return result.getBody();
    }


}
