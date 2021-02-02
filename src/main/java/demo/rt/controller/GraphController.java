package demo.rt.controller;

import demo.agent.bytebuddy.MonitorTrack;
import demo.agent.bytebuddy.Track;
import demo.agent.bytebuddy.graph.GraphVo;
import demo.rt.config.framework.Response;
import demo.rt.service.TreeService;
import demo.rt.service.vo.Node;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 链路追踪
 */
@Slf4j
@RestController
@RequestMapping(value = "/GraphController")
public class GraphController {

    @Autowired
    private TreeService treeService;


    @ApiOperation(value = "获取关系图demo")
    @GetMapping(value = "/getGraphDemo")
    public Response getGraphDemo() {
        GraphVo graphVo = GraphVo.builder("test")
                .builderNode("a", "a")
                .builderNode("b", "b")
                .builderNode("c", "c")
                .builderLink("a", "b", "xxx")
                .builderLink("a", "c", "xxx")
                .builderLink("b", "c", "xxx");
        return Response.Ok(graphVo);
    }

    @ApiOperation(value = "获取关系图Track")
    @GetMapping(value = "/getGraphTrack")
    public Response getGraphTrack() {
        GraphVo graphVo = GraphVo.builder("root");
        graphVo.builderNode("root", "root");
        MonitorTrack.rootTracks.forEach(track -> {
            graphVo.builderLink("root", track.getUuid(), "");
        });
        buildGraph(MonitorTrack.rootTracks, graphVo, 5, 0);
        return Response.Ok(graphVo);
    }

    private void buildGraph(Collection<Track> tracks, GraphVo graphVo, int level, int i) {
        if (i >= level) {
            return;
        }
        tracks.forEach(track -> {
            graphVo.builderNode(track.getUuid(), track.getSourceMethod().toString());
            track.getChild().forEach(childTrack -> {
                graphVo.builderLink(track.getUuid(), childTrack.getUuid(), "");
            });
            buildGraph(track.getChild(), graphVo, level, i + 1);
        });
    }

    @ApiOperation(value = "获取关系图分类树")
    @GetMapping(value = "/getGraphCategoryTree")
    public Response getGraphCategoryTree() throws IOException {
        Collection<Collection<Node>> categoryTree = treeService.getCategoryTree();
        Collection<Node> result = new ArrayList<>();
        categoryTree.forEach(node -> {
            result.add(new ArrayList<>(node).get(0));
        });

        GraphVo graphVo = GraphVo.builder("root");
        graphVo.builderNode("root", "root");
        result.forEach(node -> {
            graphVo.builderLink("root", node.getUuid(), "");
        });
        buildGraph(result, graphVo);
        return Response.Ok(graphVo);
    }

    private void buildGraph(Collection<Node> nodes, GraphVo graphVo) {
        nodes.forEach(node -> {
            graphVo.builderNode(node.getUuid(), node.getData());
            node.initChilds().forEach(childNode -> {
                graphVo.builderLink(node.getUuid(), childNode.getUuid(), "");
            });
            buildGraph(node.initChilds(), graphVo);
        });
    }

}
