/**
 * Copyright 2020 bejson.com
 */
package demo.rt.po.response.buckets;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import demo.rt.po.response.ESResponse;
import lombok.Data;

import java.util.List;

@Data
public class BucketsRoot {
    @JSONField(name = "doc_count_error_upper_bound")
    private int doc_count_error_upper_bound;
    @JSONField(name = "sum_other_doc_count")
    private int sum_other_doc_count;
    @JSONField(name = "buckets")
    private List<Buckets> buckets;

    public static ESResponse parse(String root) {
        ESResponse esResponse = JSONObject.parseObject(root, ESResponse.class);
        return esResponse;
    }

}