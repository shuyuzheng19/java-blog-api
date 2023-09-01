package com.zsy.model.request;

import com.github.pagehelper.dialect.helper.PostgreSqlDialect;
import lombok.Data;

/**
 * @author 郑书宇
 * @create 2023/8/30 19:44
 * @desc
 */
@Data
public class BlogPagingRequest {
    //第几页
    private int page = 1;
    //分类ID
    private int cid = -1;
    //排序方式
    private Sort sort = Sort.CREATE;

   public enum Sort{
        CREATE("create_at desc"),LIKE("like_count desc"),UPDATE("update_at desc"),BACK("create_at asc"),EYE("eye_count desc");

        private String value;

        Sort(String sort){
            this.value=sort;
        }

        public String getValue() {
            return value;
        }
    }

}
