///*
// * Copyright (c) 2019 Nanjing Wind Information Co., Ltd. All Rights Reserved;
// * @Package cn.com.wind.Wind.BDG.BussinessDataProcess.process.doc.bussiness.gsjgg
// * @version 1.0.0
// * @Date 2019年5月30日
// * @author rliu.ralph
// */
//package demo.rt.service;
//
//
//import demo.rt.service.dao.DAO1022;
//import demo.rt.service.dao.DAO4826;
//import demo.rt.service.dao.DAO4827;
//import demo.rt.service.dao.DAO4828;
//import demo.rt.service.vo.Node;
//import demo.rt.service.vo.Tb4827;
//import demo.rt.service.vo.Tb4828;
//import demo.rt.util.TreePluginUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.*;
//
//
//@Slf4j
//@Service("TreeService")
//public class TreeService {
//
//
//    @Autowired
//    private DAO4826 dao4826;
//
//    @Autowired
//    private DAO4827 dao4827;
//
//    @Autowired
//    private DAO4828 dao4828;
//
//    @Autowired
//    private DAO1022 dao1022;
//
//
//    /**
//     * 获取分类树
//     */
//    public Collection<Collection<Node>> getCategoryTree() throws IOException {
//        return dealCategory();
//    }
//
//    private Collection<Collection<Node>> dealCategory() throws IOException {
//        Collection<Collection<Node>> result = new ArrayList<>();
//        List<Tb4827> tb4827s = dao4827.queryAll();
//        Collection<String> allWords = TreePluginUtils.getAllWords();//全部指标
//        int i = 0;
//        for (Tb4827 tb4827 : tb4827s) {
//            if (tb4827.getF2_4827().equals("人")) {
//                //暂时排除人
//                continue;
//            }
//            List<Tb4828> tb4828s = dao4828.queryByF1(tb4827.getF1_4827());
//
//            List<String> collection = new ArrayList<>();
//            tb4828s.forEach(tb4828 -> {
//                if (tb4828.getF4_4828().equals("1")) {
//                    collection.add(tb4828.getF3_4828());
//                }
//            });
//            Collection<Node> nodes = singleDeal(allWords, collection, tb4827.getF2_4827());
//            result.add(nodes);//收集树
//            log.info("处理进度:{}/{}", i++, tb4827s.size());
//            if (i > 6) {
//                break;
//            }
//        }
//        return result;
//    }
//
//    /**
//     * @param collection 类型下的集合
//     * @return
//     */
//    private Collection<Node> singleDeal(Collection<String> allWords, Collection<String> collection, String rootData) throws IOException {
//
//        Collection<List<String>> levels = TreePluginUtils.getLevel(allWords, collection);
//
//        List<String> tmp = new ArrayList<>();
//        levels.forEach(level -> {
//            StringBuilder str = new StringBuilder();
//            level.forEach(vo -> {
//                str.append(vo).append(",");
//            });
//            String substring = str.substring(0, str.length() - 1);
//            tmp.add(substring);
//        });
//        Node node = TreePluginUtils.generateNode(tmp);
//        if (StringUtils.isBlank(node.getData())) {
//            Set<Node> nodes = node.initChilds();
//            if (nodes.size() == 1) {
//                node = (Node) nodes.toArray()[0];
//            }
//        }
//
//        if (StringUtils.isNotBlank(node.getData())) {
//            //如果不为空 -> 向前新增一个节点
//            Node nodeTmp = new Node();
//            nodeTmp.setData(rootData);
//            node.setFather(nodeTmp);
//            nodeTmp.initChilds().add(node);
//            node = nodeTmp;//向前移动
//        } else {
//            node.setData(rootData);
//        }
//
//        Node nodeError = TreePluginUtils.generateErrorNode(tmp, "出现循环");
//        List<Node> nodes = null;
//        if (nodeError.initChilds().size() > 0) {
//            nodes = Arrays.asList(node, nodeError);
//        } else {
//            nodes = Arrays.asList(node);
//        }
//        return nodes;
//    }
//
//
//}
