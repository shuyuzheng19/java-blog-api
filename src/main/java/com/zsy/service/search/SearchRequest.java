package com.zsy.service.search;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/30 23:41
 * @desc
 */
@Builder @Getter @NoArgsConstructor @AllArgsConstructor
public class SearchRequest implements Serializable {
    private String q;
    private Integer offset;
    private Integer limit;
    private Integer page;
    private String highlightPreTag;
    private String highlightPostTag;
    private Boolean showMatchesPosition;
    private List<String> sort;
    private List<String> attributesToHighlight;
}
