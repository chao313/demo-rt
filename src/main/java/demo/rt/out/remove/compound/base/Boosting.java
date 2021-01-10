package demo.rt.out.remove.compound.base;

import lombok.Data;

/**
 * <pre>
 * {
 *     "query": {
 *         "boosting" : {
 *             "positive" : {
 *                 "term" : {
 *                     "text" : "apple"
 *                 }
 *             },
 *             "negative" : {
 *                  "term" : {
 *                      "text" : "pie tart fruit crumble tree"
 *                 }
 *             },
 *             "negative_boost" : 0.5
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class Boosting {
}
