package com.zsy.service.search;

import lombok.Data;

/**
 * @author 郑书宇
 * @create 2023/8/30 23:42
 * @desc
 */
@Data
public class SearchResponse {
    private Object hits;
    private int offset;
    private int limit;
    private long estimatedTotalHits;
    private long totalHits;
    private int totalPages;
    private int hitsPerPage;
    private int page;
    private int processingTimeMs;
    private String query;
}
