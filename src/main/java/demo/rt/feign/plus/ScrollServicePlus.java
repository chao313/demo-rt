package demo.rt.feign.plus;

import demo.rt.feign.ScrollService;
import demo.rt.po.response.ESResponse;
import demo.rt.po.response.InnerHits;
import demo.rt.thread.ThreadLocalFeign;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * scroll是加强版本
 */
@Service
public class ScrollServicePlus {

    /**
     * 反序列化scroll的结果
     */
    public ESResponse _search(String scroll, String scroll_id) {
        ScrollService scrollService = ThreadLocalFeign.getFeignService(ScrollService.class);
        String result = scrollService._search(scroll, scroll_id);
        return ESResponse.parse(result);
    }

    /**
     * 反序列化scroll的结果（这里赋予实现者停止的权限）
     *
     * @param function 处理获取的结果集+返回是否继续检索
     */
    public void _search(String scroll, String scroll_id, Function<InnerHits, Boolean> function) {
        List<InnerHits> hits = new ArrayList<>();
        Boolean isGoon = true;//是否继续检索
        do {
            hits = this._search(scroll, scroll_id).getHits().getHits();
            for (InnerHits innerHits : hits) {
                isGoon = function.apply(innerHits);
                if (false == isGoon) {
                    //如果判断不继续 就跳出当前循环
                    break;
                }
            }
        } while (hits.size() > 0 && isGoon == true);

    }
}
