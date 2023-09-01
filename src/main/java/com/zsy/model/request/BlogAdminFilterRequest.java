package com.zsy.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class BlogAdminFilterRequest {
    private Integer page;
    private BlogPagingRequest.Sort sort = BlogPagingRequest.Sort.CREATE;
    private Integer category;
    private Long start;
    private Long end;
    private Integer topic;
    private String keyword;
    @JsonIgnore
    private Date startDate;
    @JsonIgnore
    private Date endDate;
}
