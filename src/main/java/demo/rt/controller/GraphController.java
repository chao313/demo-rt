//package demo.rt.controller;
//
//import demo.agent.bytebuddy.MonitorTrack;
//import demo.agent.bytebuddy.Track;
//import demo.agent.bytebuddy.TrackInfo;
//import demo.agent.bytebuddy.graph.GraphVo;
//import demo.rt.config.framework.Response;
//import demo.rt.service.TreeService;
//import demo.rt.service.vo.Node;
//import demo.rt.util.TreePluginUtils;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.*;
//
///**
// * 链路追踪
// */
//@Slf4j
//@RestController
//@RequestMapping(value = "/GraphController")
//public class GraphController {
//
//    @Autowired
//    private TreeService treeService;
//
//
//    @ApiOperation(value = "获取关系图demo")
//    @GetMapping(value = "/getGraphDemo")
//    public Response getGraphDemo() {
//        GraphVo graphVo = GraphVo.builder("test")
//                .builderNode("a", "a")
//                .builderNode("b", "b")
//                .builderNode("c", "c")
//                .builderLink("a", "b", "xxx")
//                .builderLink("a", "c", "xxx")
//                .builderLink("b", "c", "xxx");
//        return Response.Ok(graphVo);
//    }
//
//    @ApiOperation(value = "获取关系图Track")
//    @GetMapping(value = "/getGraphTrack")
//    public Response getGraphTrack() {
//        GraphVo graphVo = GraphVo.builder("root");
//        graphVo.builderNode("root", "root");
//        Collection<Track> filter = new HashSet<>();
//        //筛选出除去当前class的全部的root
//        MonitorTrack.rootTracks.forEach(track -> {
//            if (track.getSourceObject().getClass().equals(this.getClass())) {
//                //移除当前的class（图谱）
//                return;
//            }
//            filter.add(track);
//        });
//        filter.forEach(track -> {
//            graphVo.builderLink("root", track.getUuid(), "");
//        });
//        buildGraph(filter, graphVo, 50, 0);
//        return Response.Ok(graphVo);
//    }
//
//    /**
//     * @param tracks
//     * @param graphVo
//     * @param level   为 -1 时是不限制
//     * @param i
//     */
//    private void buildGraph(Collection<Track> tracks, GraphVo graphVo, int level, int i) {
//        if (level != -1) {
//            if (i >= level) {
//                return;
//            }
//        }
//        tracks.forEach(track -> {
//            TrackInfo trackInfo = TrackInfo.buildOnlyRoot(track);
//            graphVo.builderNode(track.getUuid(), track.getMethodName(), trackInfo);
//            track.getChild().forEach(childTrack -> {
//                graphVo.builderLink(track.getUuid(), childTrack.getUuid(), "");
//            });
//            buildGraph(track.getChild(), graphVo, level, i + 1);
//        });
//    }
//
//    @ApiOperation(value = "获取关系图Track(ByMethod)")
//    @GetMapping(value = "/getGraphTrackByMethod")
//    public Response getGraphTrackByMethod(@RequestParam(value = "method") String method) {
//        GraphVo graphVo = GraphVo.builder("root");
//        graphVo.builderNode("root", "root");
//        List<Track> tracks = MonitorTrack.mapTracks.get(method);//所有的
//        tracks.forEach(track -> {
//            graphVo.builderLink("root", track.getUuid(), "");
//            buildGraph(Arrays.asList(track), graphVo, -1, 0);
//        });
//        return Response.Ok(graphVo);
//    }
//
//    @ApiOperation(value = "获取关系图Track(@Controller)")
//    @GetMapping(value = "/getGraphTrackDefaultController")
//    public Response getGraphTrackDefaultController() {
//        GraphVo graphVo = GraphVo.builder("root");
//        graphVo.builderNode("root", "root");
//        Collection<Track> filter = new HashSet<>();
//        //过滤出Controller为的所有方法(从根节点开始)
//        MonitorTrack.rootTracks.forEach(track -> {
//            if (track.getSourceObject().getClass().equals(this.getClass())) {
//                //移除当前的class（图谱）
//                return;
//            }
//            Arrays.stream(track.getSourceObject().getClass().getDeclaredAnnotations()).forEach(annotation -> {
//                if (annotation instanceof RestController || annotation instanceof Controller) {
//                    filter.add(track);
//                }
//            });
//        });
//        filter.forEach(track -> {
//            graphVo.builderLink("root", track.getUuid(), "");
//            buildGraph(Arrays.asList(track), graphVo, -1, 0);
//        });
//        return Response.Ok(graphVo);
//    }
//
//
//    @ApiOperation(value = "获取关系图分类树")
//    @GetMapping(value = "/getGraphCategoryTree")
//    public Response getGraphCategoryTree() throws IOException {
//        Collection<Collection<Node>> categoryTree = treeService.getCategoryTree();
//        Collection<Node> result = new ArrayList<>();
//        categoryTree.forEach(node -> {
//            result.add(new ArrayList<>(node).get(0));
//        });
//
//        GraphVo graphVo = GraphVo.builder("root");
//        graphVo.builderNode("root", "root");
//        result.forEach(node -> {
//            graphVo.builderLink("root", node.getUuid(), "");
//        });
//        buildGraph(result, graphVo);
//        return Response.Ok(graphVo);
//    }
//
//    private void buildGraph(Collection<Node> nodes, GraphVo graphVo) {
//        nodes.forEach(node -> {
//            graphVo.builderNode(node.getUuid(), node.getData());
//            node.initChilds().forEach(childNode -> {
//                graphVo.builderLink(node.getUuid(), childNode.getUuid(), "");
//            });
//            buildGraph(node.initChilds(), graphVo);
//        });
//    }
//
//
//    /**
//     * 工业增加值\t规模以上
//     * \t      \t全社会
//     */
//    @ApiOperation(value = "特定格式中生成图")
//    @PostMapping(value = "/getGraphFromData")
//    public Response getGraphFromData(@RequestBody String source) throws IOException {
//        List<String> listSource = Arrays.asList(source.split("\n"));
//        Node node = TreePluginUtils.generateNodeFromExcelFormat(listSource);
//        Collection<Node> result = clean(node);
//
//
//        GraphVo graphVo = GraphVo.builder("root");
//        graphVo.builderNode("root", "root");
//        result.forEach(nodeTmp -> {
//            graphVo.builderLink("root", nodeTmp.getUuid(), "");
//        });
//        buildGraph(result, graphVo);
//        return Response.Ok(graphVo);
//    }
//
//    /**
//     * 移除无用根节点
//     *
//     * @return
//     */
//    private static Collection<Node> clean(Node root) {
//        Collection<Node> result = Arrays.asList(root);
//        if (StringUtils.isBlank(root.getData())) {
//            Set<Node> nodes = root.initChilds();
//            if (nodes.size() == 1) {
//                root = (Node) nodes.toArray()[0];
//                result = Arrays.asList(root);
//            } else {
//                result = root.initChilds();
//            }
//        }
//        return result;
//    }
//
//
//}
