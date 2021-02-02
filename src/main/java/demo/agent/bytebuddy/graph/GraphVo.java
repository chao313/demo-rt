/**
 * Copyright 2021 json.cn
 */
package demo.agent.bytebuddy.graph;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GraphVo {

    private String rootId;
    private List<Node> nodes = new ArrayList<>();
    private List<Link> links = new ArrayList<>();


    public static GraphVo builder(String rootId) {
        GraphVo graphVo = new GraphVo();
        graphVo.setRootId(rootId);
        return graphVo;
    }

    public GraphVo builderNode(String id, String text) {
        Node node = new Node(id, text);
        this.getNodes().add(node);
        return this;
    }

    public GraphVo builderLink(String from, String to, String text) {
        Link link = new Link(from, to, text);
        this.getLinks().add(link);
        return this;
    }

    /**
     * 这个是简单的，复杂的以后在说
     * http://www.relation-graph.com/#/docs/node
     */
    @Data
    public static class Node {
        public Node(String id, String text) {
            this.id = id;
            this.text = text;
        }

        private String id;//节点id，不能重复，重复会被忽略
        private String text;//text 节点名称
    }

    /**
     * 这个是简单的，复杂的以后在说
     * http://www.relation-graph.com/#/docs/link
     */
    @Data
    public static class Link {
        private String from;
        private String to;
        private String text;//关系文字

        public Link(String from, String to, String text) {
            this.from = from;
            this.to = to;
            this.text = text;
        }
    }
}