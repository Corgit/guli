package com.guli.search.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description：
 * @Author: Ryan_Wuyx
 * @Date: 2023/6/6 14:53
 */
@Data
public class EsResponseEntity {

    private Integer took;
    private Boolean timed_out;
    private Shards _shards;
    private Hits hits;

    @Data
    public static class Shards {
        private Integer total;
        private Integer failed;
        private Integer successful;
        private Integer skipped;
    }

    @Data
    public static class Hits {
        private Total total;
        private BigDecimal max_score;
        private List<SubHits> hits;
    }

    @Data
    public static class Total {
        private Integer value;
        private String relation;
    }

}
