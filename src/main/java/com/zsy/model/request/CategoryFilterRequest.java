package com.zsy.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class CategoryFilterRequest {
    private Integer page;
    private BlogPagingRequest.Sort sort = BlogPagingRequest.Sort.CREATE;
    private Long start;
    private Long end;
    private String keyword;
    private boolean deleted;
    @JsonIgnore
    private Date startDate;
    @JsonIgnore
    private Date endDate;
}
