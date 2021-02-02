package demo.rt.util;


import demo.rt.service.vo.Node;
import lombok.extern.slf4j.Slf4j;
import org.xmind.core.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 */
@Slf4j
public class XMindUtil {

    /**
     * 只有多节点的情况
     * 需要给定头
     */
    public static void builder(String rootTitleText, Collection<Node> nodes, OutputStream outputStream) throws IOException, CoreException {
        // 创建思维导图的工作空间
        IWorkbookBuilder workbookBuilder = Core.getWorkbookBuilder();
        IWorkbook workbook = workbookBuilder.createWorkbook();
        // 获得默认sheet
        ISheet primarySheet = workbook.getPrimarySheet();
        // 获得根主题
        ITopic rootTopic = primarySheet.getRootTopic();
        // 设置根主题的标题
        rootTopic.setTitleText(rootTitleText);
        // 创建章节节点
        create(workbook, rootTopic, nodes);
        // 保存
        workbook.save(outputStream);
    }

    /**
     * 处理只有一个节点的情况
     */
    public static void builder(Node node, OutputStream outputStream) throws IOException, CoreException {
        String data = "";
        if (node.getOther() != null) {
            data = node.getOther().toString() + ":" + node.getData();
        } else {
//                data = "非国标:" + node.getData();
            data = node.getData();
        }
        builder(data, node.initChilds(), outputStream);

    }

    private static void create(IWorkbook workbook, ITopic root, Collection<Node> nodes) {
//        nodes.forEach(node -> {
//            ITopic topic = workbook.createTopic();
//            topic.setTitleText(node.getData() + (node.getOther() == null ? "" : ":" + node.getOther()));
//            root.add(topic);
//            create(workbook, topic, node.initChilds());
//        });
        nodes.forEach(node -> {
            ITopic topic = workbook.createTopic();
            String data = "";
            if (node.getOther() != null) {
                data = node.getOther().toString() + ":" + node.getData();
            } else {
//                data = "非国标:" + node.getData();
                data = node.getData();
            }
            topic.setTitleText(data);
            root.add(topic);
            create(workbook, topic, node.initChilds());
        });
    }

    private static void findLastNode(Node root, String word, AtomicReference<List<Node>> targetNodes) {
        if (!root.getData().equals(word)) {
            root.initChilds().forEach(child -> {
                findLastNode(child, word, targetNodes);
            });
        } else {
            targetNodes.get().add(root);
        }

    }

    public static void builderList(String rootTitleText, List<List<String>> lists, OutputStream outputStream) throws IOException, CoreException {
        // 读取目录

        // 创建思维导图的工作空间
        IWorkbookBuilder workbookBuilder = Core.getWorkbookBuilder();
        IWorkbook workbook = workbookBuilder.createWorkbook();

        // 获得默认sheet
        ISheet primarySheet = workbook.getPrimarySheet();

        // 获得根主题
        ITopic rootTopic = primarySheet.getRootTopic();
        // 设置根主题的标题
        rootTopic.setTitleText(rootTitleText);

        // 创建章节节点
        Node root = new Node();
        root.setData("111");
        lists.forEach(list -> {
            for (int i = 0; i < list.size(); i++) {
                String word = list.get(i);
                Node node = new Node();
                node.setData(word);
                if (i == 0) {
                    root.initChilds().add(node);
                    node.setFather(root);
                    continue;
                }
                AtomicReference<List<Node>> targetNode = new AtomicReference<>(new ArrayList<>());
                findLastNode(root, list.get(i - 1), targetNode);
                int finalI = i;
                if (targetNode.get().size() == 0) {
                    log.info("异常");
                } else {
                    targetNode.get().forEach(lastNode -> {
                        String sb = "[";
                        Node tmp = lastNode;
                        do {
                            sb = sb + "," + tmp.getData();
                            tmp = tmp.getFather();
                        } while (tmp != null);
                        sb += "]";
                        String s = Arrays.toString(list.subList(finalI, list.size()).toArray());
                        if (s.equals(sb)) {
                            log.info("ok");
                        }
                    });
                }
            }
        });


//        create(workbook, rootTopic, nodes);

        // 保存
        workbook.save(outputStream);

    }


}
