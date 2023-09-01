package com.zsy.model.response;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 郑书宇
 * @create 2023/8/30 21:24
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResult implements Serializable {
    private int page;
    private int size;
    private long total;
    private List data;
}
