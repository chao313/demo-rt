package demo.rt.service.vo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class Node {
    private String uuid = java.util.UUID.randomUUID().toString().replace("-", "");//唯一id
    private Node father;
    private String data;
    private Object other;
    private Set<Node> childs;


    public synchronized Set<Node> initChilds() {
        if (null == childs) {
            childs = new LinkedHashSet<>();
        }
        return childs;
    }


    public String getUuid() {
        return uuid;
    }

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Set<Node> getChilds() {
        return childs;
    }

    public void setChilds(Set<Node> childs) {
        this.childs = childs;
    }

    public <T> Object getOther() {
        return (T) other;
    }

    public void setOther(Object other) {
        this.other = other;
    }

    @Override
    public String toString() {
        List<String> lists = new ArrayList<>();
        transformToList(this, lists, "");
        return "Node{" +
                "father=" + (father == null ? "" : father.data) +
                ", data='" + data + '\'' +
                ", childs=" + (childs == null ? 0 : childs.size()) +
                ", path=" + lists +
                '}';
    }

    /**
     * 生成多个单叉树
     *
     * @param lists
     * @return
     */
    public static Collection<Node> buildEntity(List<List<String>> lists) {

        List<Node> nodes = new ArrayList<>();
        lists.forEach(list -> {
            //第一步,先构建各个孤立树
            Node rootTmp = new Node();
            int i = 0;
            for (String word : list) {
                if (i == 0) {
                    //设置父节点
                    rootTmp.setData(word);
                    i++;
                } else {
                    //
                    Node tmp = new Node();
                    tmp.setData(word);
                    List<Node> lastNodes = new ArrayList<>();
                    Node.findLastNodes(rootTmp, lastNodes);
                    if (lastNodes == null) {
                        log.info("当前处理:{}:{}", list, word);
                        throw new RuntimeException("构造出现问题");
                    }
                    //目前只会一个
                    lastNodes.get(0).initChilds().add(tmp);
                    tmp.setFather(lastNodes.get(0));

                }
            }
            nodes.add(rootTmp);
        });
        return nodes;
    }


    /**
     * 判断生成树的基础数据是否存在循环
     *
     * @param lists
     * @param cycleData 存放循环数据
     * @return
     */
    public static boolean checkCycle(List<List<String>> lists, Collection<Map<String, String>> cycleData) {
        //第一步:构造全部的kv -> ABC -> AB BC AC
        List<Map<String, String>> tmpMapList = new ArrayList<>();
        lists.forEach(line -> {
            String[] split = new String[line.size()];
            line.toArray(split);
            for (int i = 0; i < split.length; i++) {
                for (int j = i + 1; j < split.length; j++) {
                    Map<String, String> map = new HashMap<>();
                    if (i != j) {
                        map.put(split[i], split[j]);
                        tmpMapList.add(map);
                    }
                }
            }
        });
        //第一步:拼接为顺序的数据 A,B -> AB
        Set<String> uniq = new HashSet<>();
        tmpMapList.forEach(map -> {
            map.forEach((key, value) -> {
                String str = key + value;
                uniq.add(str);

            });
        });
        //第三步:拼接为倒序的数据 A,B -> BA
        boolean flag = false;
        for (Map<String, String> map : tmpMapList) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String str = value + key;
                if (uniq.contains(str)) {
                    log.error("出现循环:{}", map);
                    flag = true;
                    if (cycleData != null) {
                        cycleData.add(map);
                    }
                }

            }
        }
        return flag;
    }

    /**
     * 节点转换成list
     *
     * @param node
     * @param collection
     * @param datas
     */
    public static void transformToList(Node node, Collection<String> collection, String datas) {
        String tmp = "";
        if (StringUtils.isNotBlank(datas)) {
            tmp = datas + "@" + node.getData();
        } else {
            tmp = node.getData();
        }
        if (null != node.getChilds() && node.getChilds().size() > 0) {
            for (Node childNode : node.getChilds()) {
                transformToList(childNode, collection, tmp);
            }
        } else {
            collection.add(tmp);
        }
    }

    /**
     * 由叶子节点 -> 获取从根节点到叶子节点的path
     *
     * @param node
     * @return
     */
    public static String transformLeavesToRootList(Node node) {
        StringBuilder tmp = new StringBuilder();
        Node tmpNode = node;
        do {
            tmp.insert(0, tmpNode.getData());
            tmpNode = tmpNode.getFather();
        } while (tmpNode != null && tmpNode.getFather() != null);
        return tmp.toString();
    }

    /**
     * 获取单叉树的path
     *
     * @param node
     * @return
     */
    private static String getLineTreeNodePath(Node node) {
        String nodeStr = "";
        List<String> list = new ArrayList<>();
        transformToList(node, list, "");
        if (list.size() > 1 || list.size() == 0) {
            throw new RuntimeException("异常");
        } else {
            nodeStr = list.get(0);
        }
        return nodeStr;
    }


    /**
     * 节点转换成list
     *
     * @param collection
     * @param datas
     */
    public static void transformToListMap(Collection<Node> nodes, Collection<Map<String, Node>> collection, String datas) {
        nodes.forEach(node -> {
            String tmp = "";
            if (StringUtils.isNotBlank(datas)) {
                tmp = datas + "@" + node.getData();
            } else {
                tmp = node.getData();
            }
            if (null != node.getChilds() && node.getChilds().size() > 0) {
                transformToListMap(node.getChilds(), collection, tmp);
            } else {
                Map<String, Node> tmpMap = new HashMap<>();
                tmpMap.put(tmp, node);
                collection.add(tmpMap);
            }
        });

    }

    /**
     * 找到最后一个没有叶子的节点(全部)
     */
    public static void findLastNodes(Node root, List<Node> lastNodes) {
        if (root.initChilds().size() == 0) {
            lastNodes.add(root);
        }
        if (root.initChilds().size() > 0) {
            root.initChilds().forEach(child -> {
                findLastNodes(child, lastNodes);
            });
        }
    }

    /**
     * 找到第一个节点
     */
    public static void findFirstNodes(Node node, AtomicReference<Node> firstNode) {
        if (node.getFather() == null) {
            firstNode.set(node);
        } else {
            findFirstNodes(node.getFather(), firstNode);
        }
    }


    /**
     * 找到头节点
     *
     * @param roots
     * @param data
     * @param nodes
     */
    public static void findNode(Collection<Node> roots, String data, Collection<Node> nodes) {
        for (Node root : roots) {
            if (StringUtils.isNotBlank(root.getData())) {
                if (root.getData().equals(data)) {
                    nodes.add(root);
                }
            }
            if (root.getChilds() != null && root.getChilds().size() > 0) {
                findNode(root.getChilds(), data, nodes);
            }
        }
    }

    /**
     * 找到当前节点是全部子节点的数据
     *
     * @param node
     */
    public static Set<String> getCurrentChildsDatas(Node node) {
        Set<String> set = new HashSet<>();
        node.initChilds().forEach(child -> {
            set.add(child.getData());
        });
        return set;
    }

    /**
     * 移除空节点
     *
     * @param roots
     */
    public static void removeNullNode(Collection<Node> roots) {
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node root : roots) {
            if (StringUtils.isBlank(root.getData()) && root.initChilds().size() == 0) {
                nodesToRemove.add(root);
            }
        }
        roots.removeAll(nodesToRemove);
    }


    /**
     * 排序
     *
     * @return
     */
    public static void sort(Collection<Node> roots, Comparator<Node> comparator) {
        for (Node root : roots) {
            TreeSet<Node> nodesTreeSet = new TreeSet<>(comparator);
            nodesTreeSet.addAll(root.initChilds());
            root.setChilds(nodesTreeSet);
            sort(nodesTreeSet, comparator);
        }

    }

    /**
     * 生成树(表格形式,层级格式)
     * 暂时没有符合要求
     *
     * @param node
     */
    @Deprecated
    public static void generateList(Node node, Collection<List<String>> collection, String datas) {
        String tmp = "";
        if (StringUtils.isNotBlank(datas)) {
            tmp = datas + "@" + node.getData();
        } else {
            tmp = node.getData();
        }
        if (null != node.getChilds() && node.getChilds().size() > 0) {
            for (Node childNode : node.getChilds()) {
                generateList(childNode, collection, tmp);
            }
        } else {
            String[] split = tmp.split("@");
            List<String> tmpList = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                String word = split[i];
                if (i != split.length - 1) {
                    tmpList.add("");
                } else {
                    tmpList.add(word);
                }
            }
            collection.add(tmpList);
        }

    }


    /**
     * 合并同一个Node的子节点
     * -> 如果当前节点相同,合并子节点
     */
    public static void mergeSameNodeChilds(List<Node> nodes) {
        //存放data相同的Nodes
        Map<String, Collection<Node>> dataSameNodeMap = new HashMap<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            for (int i1 = i + 1; i1 < nodes.size(); i1++) {
                Node node1 = nodes.get(i1);
                if (node.getData().equals(node1.getData())) {
                    if (dataSameNodeMap.containsKey(node.getData())) {
                        dataSameNodeMap.get(node.getData()).add(node);
                        dataSameNodeMap.get(node.getData()).add(node1);
                    } else {
                        Collection<Node> nodesSame = new HashSet<>(nodes.size() * 2);
                        nodesSame.add(node);
                        nodesSame.add(node1);
                        dataSameNodeMap.put(node.getData(), nodesSame);
                    }
                }
            }
        }
        //处理相同data的Node -> 保留一个,其他的都删除
        dataSameNodeMap.forEach((data, nodes2) -> {
            List<Node> nodesTmp = new ArrayList<>(nodes2);
            Object otherInCollection = getOtherInCollection(nodesTmp);
            if (nodesTmp.size() >= 2) {
                Node node = nodesTmp.get(0);
                node.setOther(otherInCollection);//设置other
                nodesTmp.forEach(node1 -> {
                    node.initChilds().addAll(node1.initChilds());//添加其他的全部节点
                    node.initChilds().forEach(child -> {
                        child.setFather(node);
                    });
                    if (node1 != node) {
                        if (node1.getFather() != null) {
                            node1.getFather().initChilds().remove(node1);
                        } else {
                            log.error("father为空:{}", node1);
                            nodes.remove(node1);
                        }
                    }
                });
            }
        });
        nodes.forEach(node -> {
            mergeSameNodeChilds(new ArrayList<>(node.initChilds()));
        });
    }

    /**
     * 从集合中获取other不为空的存在
     */
    private static Object getOtherInCollection(Collection<Node> nodesTmp) {
        Object result = null;
        for (Node node : nodesTmp) {
            if (null != node.getOther()) {
                result = node.getOther();
            }
        }
        return result;
    }

    /**
     * 找到data相同的节点
     */
    public static void findNodeByData(Collection<Node> nodes, String data, Collection<Node> nodesToCollection) {
        nodes.forEach(child -> {
            if (StringUtils.isNotBlank(child.getData())) {
                if (child.getData().equals(data)) {
                    nodesToCollection.add(child);
                }
            }
            findNodeByData(child.initChilds(), data, nodesToCollection);
        });

    }

    /**
     * 找到data相同的节点
     */
    public static void nodeClone(Node source, Node target) {
        target.setData(source.getData());
        target.setOther(source.getOther());
        source.initChilds().forEach(sourceChild -> {
            //子节点复制
            Node targetChild = new Node();
            targetChild.setData(sourceChild.getData());
            targetChild.setOther(sourceChild.getOther());
            targetChild.setFather(target);
            target.initChilds().add(targetChild);
            nodeClone(sourceChild, targetChild);
        });
    }

    /**
     * 把currentNode下面的数据全部添加到targetNode下面 -> 最终使用targetNode就行
     * 两个节点的data是一致的
     * <p>
     * 数据开始: 给定两个data相同的Node 目标是把Node下面的子节点合并
     *
     * @param upNode
     * @param downNode
     * @param nodeRemoves 待删除的节点
     */
    public static void mergeNodes(Node upNode, Node downNode, AtomicReference<List<Node>> nodeRemoves) {
        if (upNode == downNode) {
            //同一个节点
            return;
        }
        //这里的两个节点的data一定一致!
        if (upNode.initChilds().size() == 0 && downNode.initChilds().size() != 0) {
            //如果上级节点下没有数据 currentNode下游数据
            downNode.initChilds().forEach(child -> {
                Node node = new Node();
                node.setData(child.getData());
                node.setFather(upNode);
                node.setChilds(child.initChilds());
                upNode.initChilds().add(node);
            });
            if (null != nodeRemoves) {
                nodeRemoves.get().add(downNode);
            }
        } else if (downNode.initChilds().size() == 0) {
            //下级节点没有子节点,也没有上级节点 -> 无需操作,直接移除
            if (downNode.getFather() == null) {
                log.info("下级节点没有子节点,也没有上级节点 -> 无需操作,直接移除");
                if (null != nodeRemoves) {
                    nodeRemoves.get().add(downNode);
                }
            } else {
                if (null != upNode.getFather() && downNode.getFather().getData().equals(upNode.getFather().getData())) {
                    log.info("下级节点没有子节点,上级一样 -> 无需操作,直接移除");
                    if (null != nodeRemoves) {
                        nodeRemoves.get().add(downNode);
                    }
                }
            }
        } else {
            /**
             * 如果有子节
             * -> 子节点比较
             * -> data相同进行递归
             * -> data不同进行叠加
             */
            Collection<Map<Node, Node>> mapSet = new HashSet<>();
            for (Node downNodeChild : downNode.initChilds()) {
                for (Node upNodeChild : upNode.initChilds()) {
                    Map<Node, Node> map = new HashMap<>();
                    map.put(upNodeChild, downNodeChild);
                    mapSet.add(map);
                }
            }
            for (Map<Node, Node> map : mapSet) {
                for (Map.Entry<Node, Node> entry : map.entrySet()) {
                    Node upNodeChild = entry.getKey();
                    Node downNodeChild = entry.getValue();
                    if (upNodeChild.getData().equals(downNodeChild.getData())) {
                        //-> data相同进行递归
                        mergeNodes(upNodeChild, downNodeChild, nodeRemoves);
                    } else {
                        //-> data不同进行叠加
                        if (upNodeChild != downNodeChild) {
                            upNodeChild = upNodeChild.getFather();
                            downNodeChild = downNodeChild.getFather();
                            for (Node child1 : downNodeChild.initChilds()) {
                                Node node = new Node();
                                node.setData(child1.getData());
                                node.setChilds(child1.initChilds());
                                node.setFather(upNodeChild);
                                upNodeChild.initChilds().add(node);
                            }
//                            if (null != nodeRemoves) {
//                                nodeRemoves.get().add(downNodeChild);
//                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 自定义的节点排序
     * 1.先根据data的最后一个词比较
     * 2.再更加data的第一个词比较
     */
    public static class LocalComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            String o1data = o1.getData();
            String o2data = o2.getData();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(o1data) && org.apache.commons.lang3.StringUtils.isNotBlank(o2data)) {
                String o1end = new StringBuilder(o1data).substring(o1data.length() - 1, o1data.length());
                String o2end = new StringBuilder(o2data).substring(o2data.length() - 1, o2data.length());
                int i = o1end.compareTo(o2end);
                if (i != 0) {
                    return i;
                } else {
                    String o1start = new StringBuilder(o1data).substring(0, 1);
                    String o2start = new StringBuilder(o2data).substring(0, 1);
                    int i1 = o1start.compareTo(o2start);
                    if (i1 != 0) {
                        return i1;
                    } else {
                        return -1;
                    }
                }
            } else {
                return -1;
            }
        }
    }

    /**
     * 自定义的节点排序
     * 1.先根据 other 进行排序
     * 2.如果没有 other 直接放在最后一个
     */
    public static class SimpleComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            String o1Other = o1.getOther() == null ? "" : o1.getOther().toString();
            String o2Other = o2.getOther() == null ? "" : o2.getOther().toString();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(o1Other) && org.apache.commons.lang3.StringUtils.isNotBlank(o2Other)) {
                return o1Other.compareTo(o2Other);
            } else if (org.apache.commons.lang3.StringUtils.isNotBlank(o1Other) && org.apache.commons.lang3.StringUtils.isBlank(o2Other)) {
                return -1;
            } else if (org.apache.commons.lang3.StringUtils.isBlank(o1Other) && org.apache.commons.lang3.StringUtils.isNotBlank(o2Other)) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
