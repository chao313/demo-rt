package demo.rt.util;

import demo.rt.service.vo.Node;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.xmind.core.CoreException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
public class TreePluginUtils {


    /**
     * 构造树
     *
     * @param lists
     * @return
     */
    private static Node buildEntity(List<List<String>> lists) {

        Collection<Node> nodes = Node.buildEntity(lists);
//        log.info("node：{}", nodes);
        ArrayList<Node> tmp = new ArrayList<>(nodes);
        //
        Node root = new Node();
        tmp.forEach(node -> {
            root.initChilds().add(node);
            node.setFather(root);
        });
        List<Node> nodes1 = Arrays.asList(root);
        Node.mergeSameNodeChilds(nodes1);
        return root;
    }


    public static Node generateNode(List<String> lines) {
        List<List<String>> dataCheck = new ArrayList<>();
        lines.forEach(line -> {
            dataCheck.add(new ArrayList<>(Arrays.asList(line.split(","))));
        });
        Collection<Map<String, String>> cycleData = new ArrayList<>();
        boolean isCycle = Node.checkCycle(dataCheck, cycleData);
        if (isCycle == true) {
            log.info("出现循环:{} -> 继续", cycleData);
//            System.exit(0);
        }
        //循环:

        lines = filter(lines, cycleData);
        List<List<String>> data = new ArrayList<>();
        lines.forEach(line -> {
            data.add(new ArrayList<>(Arrays.asList(line.split(","))));
        });
        //构建树
        Node node = buildEntity(data);

        //添加额外节点
        Collection<String> collection = new HashSet<>();
        data.forEach(words -> {
            if (words.size() == 1) {
                collection.add(words.get(0));
            }
        });
        //防止出现重复的根节点
        Set<String> currentChildsDatas = Node.getCurrentChildsDatas(node);
        collection.removeAll(currentChildsDatas);
        //加入无序节点
        collection.forEach(word -> {
            Node nodeTmp = new Node();
            nodeTmp.setFather(node);
            nodeTmp.setData(word);
            node.initChilds().add(nodeTmp);
        });

        Node.sort(Arrays.asList(node), new Node.LocalComparator());
        TreeSet<Node> nodesTreeSet = new TreeSet<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.initChilds().size() == 0) {
                    return 1;
                } else {
                    return -1;
                }

            }
        });
        nodesTreeSet.addAll(node.initChilds());
        node.setChilds(nodesTreeSet);//根排序
        return node;
    }

    /**
     * 获取循环的错误的lines
     *
     * @param lines
     * @return
     */
    public static Node generateErrorNode(List<String> lines, String title) {
        List<List<String>> dataCheck = new ArrayList<>();
        lines.forEach(line -> {
            dataCheck.add(new ArrayList<>(Arrays.asList(line.split(","))));
        });
        Collection<Map<String, String>> cycleData = new ArrayList<>();
        Node.checkCycle(dataCheck, cycleData);
        lines = filterError(lines, cycleData);

        List<List<String>> data = new ArrayList<>();
        lines.forEach(line -> {
            data.add(new ArrayList<>(Arrays.asList(line.split(","))));
        });
        Collection<Node> nodes = Node.buildEntity(data);
        ArrayList<Node> tmp = new ArrayList<>(nodes);
        //
        Node root = new Node();
        root.setData(title);
        tmp.forEach(node -> {
            root.initChilds().add(node);
            node.setFather(root);
        });
        return root;
    }


    /**
     * 移除循环
     */
    private static List<String> filter(List<String> lines, Collection<Map<String, String>> cycleData) {
        List<String> result = new ArrayList<>();
        lines.forEach(line -> {
            boolean flag = true;
            for (Map<String, String> map : cycleData) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    List<String> list = Arrays.asList(line.split(","));
                    if (list.contains(key) && list.contains(value)) {
                        flag = false;//表明存在循环问题 ->
                    }

                }
            }
            if (flag == true) {
                result.add(line);
            }
        });
        return result;
    }

    /**
     * 过滤出循环的行
     */
    private static List<String> filterError(List<String> lines, Collection<Map<String, String>> cycleData) {
        List<String> result = new ArrayList<>();
        lines.forEach(line -> {
            boolean flag = true;
            for (Map<String, String> map : cycleData) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    List<String> list = Arrays.asList(line.split(","));
                    if (list.contains(key) && list.contains(value)) {
                        flag = false;//表明存在循环问题 ->
                    }

                }
            }
            if (flag == false) {
                result.add(line);
            }
        });
        return result;
    }

    /**
     * 预期为
     * 期房,住宅,140平方米以上住房
     * 期房,住宅,商品住宅
     *
     * @throws IOException
     */
    @Test
    public void testBuilder1() throws IOException {
        this.generateNode(Arrays.asList("住宅,140平方米以上住房", "住宅"));


    }

    /**
     * 预期为
     * 期房,住宅,140平方米以上住房
     * 期房,住宅,商品住宅
     *
     * @throws IOException
     */
    @Test
    public void testBuilder2() throws IOException {
        this.generateNode(Arrays.asList("住宅,140平方米以上住房", "住宅", "住宅,商品住宅", "期房,住宅"));


    }

    /**
     * 预期为 D,B,C
     *
     * @throws IOException
     */
    @Test
    public void testBuilder3() throws IOException {
        this.generateNode(Arrays.asList("D,B,C", "B,C", "C,B", "C,D", "A,B"));


    }

    /**
     * 预期为 D,B,C
     *
     * @throws IOException
     */
    @Test
    public void testBuilder31() throws IOException {
        this.generateNode(Arrays.asList("D,B,C", "B,C", "B"));
        ;

    }


    /**
     * 预期为 D,B,C
     *
     * @throws IOException
     */
    @Test
    public void testBuilder32() throws IOException {
        this.generateNode(Arrays.asList("D,B,C", "B,C", "A,B"));


    }

    /**
     * 预期为 D,B,C
     *
     * @throws IOException
     */
    @Test
    public void testBuilder33() throws IOException {
        this.generateNode(Arrays.asList("D,B", "D"));


    }

    @Test
    public void testBuilder34() throws IOException {
        this.generateNode(Arrays.asList("A,B,C", "D,B", "D"));


    }

    /**
     * 预期为 D,B,C
     *
     * @throws IOException
     */
    @Test
    public void testBuilder35() throws IOException {
        this.generateNode(Arrays.asList("D,C", "D,B", "B"));


    }

    /**
     * 预期为
     * 期房,住宅,140平方米以上住房
     * 期房,住宅,商品住宅
     *
     * @throws IOException
     */
    @Test
    public void testBuilder4() throws IOException, CoreException {
        Node node = this.generateNode(Arrays.asList("住宅,期房,公寓别墅",
                "住宅,144平方米以上住房", "办公楼,期房", "公寓", "办公楼", "期房", "住宅,90平方米以下住房",
                "住宅,别墅、高档公寓", "住宅,公寓别墅", "现房", "商业营业用房", "别墅、高档公寓", "花园住宅",
                "住宅,期房", "商业营业用房,现房", "住宅,公寓", "住宅,现房,公寓别墅", "低标住宅", "其他", "住宅,其他", "住宅,现房", "商业营业用房,期房",
                "办公楼,现房", "商品住宅", "住宅,140平方米以上住房", "住宅", "住宅,商品住宅"));

        XMindUtil.builder("房地产-房屋层级关系", Arrays.asList(node), new FileOutputStream("d:/房地产-房屋层级关系.xmind"));

    }

    /**
     * 预期为
     * 期房,住宅,140平方米以上住房
     * 期房,住宅,商品住宅
     *
     * @throws IOException
     */
    @Test
    public void testBuilder41() throws IOException {
        this.generateNode(Arrays.asList("住宅,期房,公寓别墅",
                "住宅,公寓别墅",
                "住宅,现房,公寓别墅", "住宅,其他", "住宅,现房",
                "办公楼,现房", "住宅,140平方米以上住房", "住宅", "住宅,商品住宅"));

    }

    /**
     * 预期为
     * 期房,住宅,140平方米以上住房
     * 期房,住宅,商品住宅
     *
     * @throws IOException
     */
    @Test
    public void testBuilder5() throws IOException {
        this.generateNode(Arrays.asList(
                "商业营业用房",
                "住宅,期房", "商业营业用房,现房", "商业营业用房,期房", "住宅,140平方米以上住房", "住宅,hhh"
        ));


    }

    /**
     * 预期为
     * 期房,住宅,140平方米以上住房
     * 期房,住宅,商品住宅
     *
     * @throws IOException
     */
    @Test
    public void testBuilder6() throws IOException {
        this.generateNode(Arrays.asList("办公楼,期房", "办公楼", "期房"));

    }

    /**
     * 预期为
     * 期房,住宅,140平方米以上住房
     * 期房,住宅,商品住宅
     *
     * @throws IOException
     */
    @Test
    public void testBuilder61() throws IOException {
        this.generateNode(Arrays.asList("住宅,期房,公寓别墅", "办公楼,期房", "办公楼", "期房"));
    }

    /**
     * 预期为
     * 期房,住宅,140平方米以上住房
     * 期房,住宅,商品住宅
     *
     * @throws IOException
     */
    @Test
    public void testBuilder7() throws IOException, CoreException {
        File file = new File("src/main/resources/data/存贷款分类树.csv");
        List<String> list1 = FileUtils.readLines(file, "UTF-8");
        Node node = this.generateNode(list1);
        node.setData("存贷款分类树");
        XMindUtil.builder(node, new FileOutputStream("d:/存贷款分类树.xmind"));

    }

    /**
     * @throws IOException
     */
    @Test
    public void testBuilder8() throws IOException, CoreException {
        File file = new File("src/main/resources/data/对外直接投资.csv");
        List<String> list1 = FileUtils.readLines(file, "UTF-8");
        AtomicReference<Node> listAtomicReference = new AtomicReference<>();
        Node node = this.generateNode(list1);

        XMindUtil.builder("对外直接投资", Arrays.asList(node), new FileOutputStream("d:/对外直接投资.xmind"));

    }

    /**
     * @throws IOException
     */
    @Test
    public void testBuilder9() throws IOException, CoreException {
        File file = new File("src/main/resources/data/产量树.csv");
        List<String> list1 = FileUtils.readLines(file, "UTF-8");
        AtomicReference<Node> listAtomicReference = new AtomicReference<>();
        Node node = this.generateNode(list1);

        XMindUtil.builder("产量树", Arrays.asList(node), new FileOutputStream("d:/产量树.xmind"));

    }


    /**
     * @throws IOException
     */
    @Test
    public void testBuilder10() throws IOException, CoreException {
        List<String> types = Arrays.asList("gy", "gdp", "jgzs", "gdzctz", "cz", "ny", "jzy", "dwmytz", "zj", "zz", "fang", "rk", "gnmy", "jqdc", "yhyhb", "jyygz");
//        List<String> types = Arrays.asList("gdzctz");

        Map<String, String> typeToName = new HashMap<>();
        typeToName.put("gy", "工业");
        typeToName.put("gdp", "gdp");
        typeToName.put("jgzs", "价格指数");
        typeToName.put("gdzctz", "固定资产投资");
        typeToName.put("cz", "财政");
        typeToName.put("ny", "能源");
        typeToName.put("jzy", "建筑业");
        typeToName.put("dwmytz", "对外贸易投资");
        typeToName.put("zj", "zj");
        typeToName.put("zz", "zz");
        typeToName.put("fang", "房地产");
        typeToName.put("rk", "人口");
        typeToName.put("gnmy", "国内贸易");
        typeToName.put("jqdc", "景气调查");
        typeToName.put("yhyhb", "银行与货币");
        typeToName.put("jyygz", "就业与工资");

        Map<String, Collection<String>> map = new LinkedHashMap<>();//存放type -> 具体数据
        File file = new File("src/main/resources/data/核心指标/AllData4826");
        List<String> list = FileUtils.readLines(file, "UTF-8");
        list.forEach(line -> {
            String[] split = line.split("\t");
            if (split.length != 2) {
                throw new RuntimeException("文件结构出现问题");
            }
            types.forEach(type -> {
                if (split[0].startsWith(type)) {
                    if (map.containsKey(type)) {
                        map.get(type).add(split[1]);
                    } else {
                        Set<String> hashSet = new HashSet<>();
                        hashSet.add(split[1]);
                        map.put(type, hashSet);
                    }
                }
            });
        });
        Collection<String> allWords = getAllWords();//全部指标

        for (Map.Entry<String, Collection<String>> entry : map.entrySet()) {
            String type = entry.getKey();
            Collection<String> collection = entry.getValue();
            log.info("当前处理:{}", type);
            Collection<List<String>> levels = getLevel(allWords, collection);
            AtomicReference<Node> listAtomicReference = new AtomicReference<>();

            List<String> tmp = new ArrayList<>();
            levels.forEach(level -> {
                StringBuilder str = new StringBuilder();
                level.forEach(vo -> {
                    str.append(vo).append(",");
                });
                String substring = str.substring(0, str.length() - 1);
                tmp.add(substring);
            });
            Node node = this.generateNode(tmp);
            if (StringUtils.isBlank(node.getData())) {
                Set<Node> nodes = node.initChilds();
                if (nodes.size() == 1) {
                    node = (Node) nodes.toArray()[0];
                }
            }
            XMindUtil.builder(typeToName.get(type), Arrays.asList(node), new FileOutputStream("d:/" + typeToName.get(type) + ".xmind"));
        }


    }


    /**
     * 获取全部的指标
     *
     * @return
     * @throws IOException
     */
    public static Collection<String> getAllWords() throws IOException {
        File file = new File("D:/wind/wcb/Wind.BDG.EDB.IndicatorService/1214_1.txt");
        List<String> list = FileUtils.readLines(file, "UTF-8");
        Set<String> words = new HashSet<>();
        for (String line : list) {
            String $2 = line.replaceAll("(.*?)@.*", "$1");
            words.add($2);
        }
        return words;
    }

    /**
     * 获取层级
     *
     * @param allWords
     * @param allTargetWords
     * @return
     */
    public static Collection<List<String>> getLevel(Collection<String> allWords, Collection<String> allTargetWords) {
        Set<List<String>> resultSet = new HashSet<>();
        allWords.forEach(word -> {
            String[] split = word.split(":");
            List<String> wordToSplit = new ArrayList<>(Arrays.asList(split));
            wordToSplit.retainAll(allTargetWords);
            if (wordToSplit.size() >= 2) {
                resultSet.add(wordToSplit);
            }
        });
        //加入单独的根节点
        Collection<String> no = findNo(resultSet, allTargetWords);//找到不存在关系的
        no.forEach(word -> {
            resultSet.add(Arrays.asList(word));
        });
        return resultSet;
    }

    /**
     * 找到没有层级的word
     *
     * @param result
     * @param allTargetWords
     * @return
     */
    public static Collection<String> findNo(Set<List<String>> result, Collection<String> allTargetWords) {
        Set<String> tmp = new HashSet<>();
        result.forEach(words -> {
            tmp.addAll(new ArrayList<>(words));
        });
        allTargetWords.removeAll(tmp);
        return allTargetWords;
    }

    /**
     * 获取国标行业
     */
    @Test
    public void generateEDBFormatRoot() throws IOException, CoreException {
        Node formatRoot = getFormatRoot();
        XMindUtil.builder("EDB", Arrays.asList(formatRoot), new FileOutputStream("d:/EDB.xmind"));
    }

    @Test
    public void generateEDBFormatRootMax() throws IOException, CoreException {

        List<String> list = org.apache.commons.io.FileUtils.readLines(new File("src/main/resources/data/最大公共子树/repeat"), "UTF-8");
        Map<String, String> map = new HashMap<>();
        list.forEach(line -> {
            String[] split = line.split("\t");
            map.put(split[0], split[1]);
        });
        Node formatRoot = getFormatRoot();
        clean(formatRoot);
        Map<String, Collection<Node>> allMap = new HashMap<>();
        map.forEach((data, size) -> {
            Collection<Node> nodesToCollection = new HashSet<>();
            Node.findNodeByData(Arrays.asList(formatRoot), data, nodesToCollection);//找到相同数据的根节点
            Collection<Node> nodesToCollectionCopy = new HashSet<>();
            nodesToCollection.forEach(node -> {
                Node target = new Node();
                Node.nodeClone(node, target);//复制节点
                nodesToCollectionCopy.add(target);//收集节点
            });
            allMap.put(data, nodesToCollectionCopy);
        });


//        System.out.println("all：{}" + allMap);
//        for (Map.Entry<String, Collection<Node>> entry : allMap.entrySet()) {
//            String data = entry.getKey();
//            Collection<Node> coll = entry.getValue();
//            Node target = new Node();//目标
//            target.setData(data);
//            findMax(coll, target);
//            log.info("target:{}", target);
//            if (target.initChilds().size()>0) {
//                XMindUtil.builder(data, Arrays.asList(target), new FileOutputStream("d:/最大公共子树/" + data + ".xmind"));
//            }
//        }

        ///

        for (Map.Entry<String, Collection<Node>> entry : allMap.entrySet()) {
            String data = entry.getKey();
            Collection<Node> coll = entry.getValue();
            Node target = new Node();//目标
            target.setData("title");
            coll.forEach(vo -> {
                vo.setFather(target);
            });
            target.initChilds().addAll(coll);
            Node.mergeSameNodeChilds(Arrays.asList(target));
            Node.sort(Arrays.asList(target), new Node.SimpleComparator());
//            XMindUtil.builder(data, Arrays.asList(target), new FileOutputStream("d:/最大公共子树2/" + data.replace("?", "") + ".xmind"));
            XMindUtil.builder(target, new FileOutputStream("d:/最大公共子树2/" + data.replace("?", "") + ".xmind"));
            //移除无关节点（包括单个节点）
            Node first = findFirst(target);
            if (first.initChilds().size() > 0) {
                XMindUtil.builder(first, new FileOutputStream("d:/最大公共子树3/" + data.replace("?", "") + ".xmind"));
            }
            if (first.initChilds().size() > 0) {
                if (first.initChilds().size() == 1) {
                    if (((Node) (first.initChilds().toArray()[0])).getData().equals(first.getData())) {
                        //当只有一个子节点,并且父子节点相同 -> 直接抛弃
                        continue;
                    }
                }
                XMindUtil.builder(first, new FileOutputStream("d:/最大公共子树4/" + data.replace("?", "") + ".xmind"));
            }

        }
    }

    private Node findFirst(Node source) {
        if (source.getData().equals("title")) {
            if (source.initChilds().size() != 1) {
                throw new RuntimeException("结构发生错误");
            } else {
                source = (Node) source.initChilds().toArray()[0];
            }
        }
        return source;
    }

    /**
     * 找到最大公共子树
     *
     * @param nodes : 根节点相同的nodes
     * @param nodes : 目标节点
     */
    private void findMax(Collection<Node> nodes, Node target) {
        Set<String> dataSet = new HashSet<>();
        List<Collection<String>> total = new ArrayList<>();//获取全部的集合
        nodes.forEach(node -> {
            Collection<String> tmp = new ArrayList<>();
            node.initChilds().forEach(child -> {
                tmp.add(child.getData());
            });
            total.add(tmp);
        });
        Collection<String> intersectionCollection = findIntersection(total);
        log.info("intersection:{}", intersectionCollection);//交集
        //根据交集进行分门别类
        Map<String, Collection<Node>> map = new HashMap<>();
        intersectionCollection.forEach(intersection -> {
            nodes.forEach(node -> {
                Collection<String> tmp = new ArrayList<>();
                node.initChilds().forEach(child -> {
                    if (child.getData().equals(intersection)) {
                        if (map.containsKey(intersection)) {
                            map.get(intersection).add(child);
                        } else {
                            Collection<Node> tmpColl = new HashSet<>();
                            tmpColl.add(child);
                            map.put(intersection, tmpColl);
                        }
                    }
                });
            });
        });
        log.info("分门别类数据：{}", map);
        map.forEach((data, coll) -> {
            Node tmp = new Node();
            tmp.setData(data);
            tmp.setFather(target);
            target.initChilds().add(tmp);
            findMax(coll, tmp);
        });
    }

    /**
     * 找到所给的集合的交集
     *
     * @return
     */
    private Collection<String> findIntersection(List<Collection<String>> sources) {
        assert sources.size() > 0;
        Collection<String> collection = sources.get(0);//获取第一个集合
        sources.forEach(source -> {
            collection.retainAll(source);
        });
        return collection;
    }

    /**
     * 获取国标行业
     *
     * @return
     */

    public Node getFormatRoot() throws IOException, CoreException {
        File file = new File("src/main/resources/data/4837/4837");
        List<String> list = org.apache.commons.io.FileUtils.readLines(file, "UTF-8");
        List<Node> roots = new ArrayList<>();
        list.forEach(line -> {
            String[] split = line.split("\t");

            if (split.length != 3) {
                //长度不为2 -> 异常
                throw new RuntimeException("文件格式存在问题");
            }
            String code = split[2];

            Node root = new Node();
            int length = code.length();//需求切割的长度
            for (int i = 0; i < length - 3; i = i + 3) {
                String levelCode = code.substring(i, i + 3);//层级code
                if (levelCode.equals("000")) {
                    //代表到达尾部节点
                    break;
                }
                Node nodeTmp = new Node();
                nodeTmp.setData(levelCode);//当前等级
                nodeTmp.setFather(root);//设置父级
                root.initChilds().add(nodeTmp);
                root = nodeTmp;
            }
            root.setOther(split[1]);//存储其他数据
            AtomicReference<Node> firstNode = new AtomicReference<>();
            Node.findFirstNodes(root, firstNode);
            if ((firstNode.get().initChilds().size() != 1)) {
                throw new RuntimeException("数据结构异常");
            }
            roots.add((Node) firstNode.get().initChilds().toArray()[0]);
        });
//        log.info("roots:{}", roots);
        Node root = new Node();
        roots.forEach(tmp -> {
            root.initChilds().add(tmp);
            tmp.setFather(root);
        });
//        clean(root);
        Node.mergeSameNodeChilds(Arrays.asList(root));
        log.info(":{}", root);
//        clean(root);
        Node.sort(Arrays.asList(root), new Node.SimpleComparator());
        return root;
    }

    /**
     * 交换data和other
     */
    private void clean(Node node) {
        if (null != node.getOther()) {
            String data = node.getData();
            String other = node.getOther().toString();
            node.setData(other);
            node.setOther(data);
        }
        node.initChilds().forEach(child -> {
            clean(child);
        });
    }

    /**
     * @throws IOException
     */
    @Test
    public void testBuilder11() throws IOException, CoreException {
        File file = new File("src/main/resources/data/trie");
        List<String> list1 = FileUtils.readLines(file, "UTF-8");
        Node root = this.generateNode(new ArrayList<>(list1));

        String path = "d:/trie/" + "trie" + ".xmind";
        XMindUtil.builder("trie", Arrays.asList(root), new FileOutputStream(path));
        System.out.println("结果路径:" + path);
    }

    /**
     * 补充 落盘数据(单独)
     */
    @Test
    public void makeupSingle() throws IOException, CoreException {
        String path = "D:/结果/correct";
        File dir = new File(path);

        for (File file : dir.listFiles()) {
            //遍历子节点(判断为文件夹)
            List<String> listSource = new ArrayList<>();
            File[] files = file.listFiles();
            if (null != files) {
                //如果是文件夹
                for (File tmp : files) {
                    List<String> list = org.apache.commons.io.FileUtils.readLines(tmp, "UTF-8");
                    listSource.addAll(list);
                }
            } else {
                //如果是单个文件
                List<String> list = org.apache.commons.io.FileUtils.readLines(file, "UTF-8");
                listSource.addAll(list);
            }
            Node node = this.build(listSource);
            log.info("运行结果:{}", node);
            String pathPre = "d:/结果/补充single/" + file.getName();
            XMindUtil.builder(file.getName(), Arrays.asList(node), new FileOutputStream(pathPre + ".xmind"));

            //
            List<String> wordIn4837And4826 = getWordIn4837And4826();
            //获取新的词
            Collection<String> newWords = filterWords(node.initChilds(), wordIn4837And4826);

            IOUtils.write(Arrays.toString(newWords.toArray()), new FileOutputStream(pathPre + ".txt"), "UTF-8");
        }
    }


    /**
     * 补充 落盘数据（全部）
     */
    @Test
    public void makeupAll() throws IOException, CoreException {
        String path = "src/main/resources/data/makeup/correct.txt";
        File file = new File(path);
        List<String> listSource = org.apache.commons.io.FileUtils.readLines(file, "UTF-8");
        Node node = this.build(listSource);
        log.info("运行结果:{}", node);
        XMindUtil.builder("补充", Arrays.asList(node), new FileOutputStream("d:/补充.xmind"));
    }


    private Node build(List<String> listSource) throws IOException {
        List<List<String>> listResult = new ArrayList<>();
        listSource.forEach(line -> {
            String[] split = line.split("~~");
            if (split.length == 3) {
                listResult.add(new ArrayList<>(Arrays.asList(split)));
            } else {
                log.info("异常数据,抛弃:{}", line);
            }

        });
        //整合成目标数据
        List<List<String>> listResultClean = new ArrayList<>();
        listResult.forEach(list -> {
            String s = list.get(0);
            String word = list.get(2);
            List<String> tmp = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(s) - 1; i++) {
                tmp.add("");
            }
            tmp.add(word.trim());
            listResultClean.add(tmp);
        });

        for (int i = 0; i < listResultClean.size(); i++) {
            List<String> list = listResultClean.get(i);
            for (int i1 = 0; i1 < list.size(); i1++) {
                String word = list.get(i1);
                if (StringUtils.isBlank(word)) {
                    list.set(i1, listResultClean.get(i - 1).get(i1));
                }
            }
        }
        Node node = buildEntity2(listResultClean);
        return node;
    }

    private Node buildEntity2(List<List<String>> lists) {

        Collection<Node> nodes = Node.buildEntity(lists);
        log.info("node：{}", nodes);
        ArrayList<Node> tmp = new ArrayList<>(nodes);
        //
        Node root = new Node();
        tmp.forEach(node -> {
            root.initChilds().add(node);
            node.setFather(root);
        });
        List<Node> nodes1 = Arrays.asList(root);
        Node.mergeSameNodeChilds(nodes1);
        root.getChilds().forEach(child -> {
            child.setFather(root);
        });
        return root;
    }


    /**
     * 节点数据和库中数据进行比较（保留节点是的数据）
     */
    private static Collection<String> filterWords(Collection<Node> nodes, Collection<String> wordIn4837And4826) {
        Collection<String> data = new HashSet<>();
        getDataList(nodes, data);
        data.removeAll(wordIn4837And4826);
        return data;
    }

    /**
     * 获取树中的全部的词
     */
    private static void getDataList(Collection<Node> nodes, Collection<String> data) {
        nodes.forEach(node -> {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(node.getData())) {
                data.add(node.getData());
            }
            getDataList(node.initChilds(), data);
        });
    }

    /**
     * 4826中的核心词和别名+在4837大树中的是已经存在的词
     */
    private static List<String> getWordIn4837And4826() throws IOException {
        List<String> list4837 = new ArrayList<>();
        List<String> list4826 = new ArrayList<>();
        File file = new File("src/main/resources/data/4837/4837");
        List<String> list = org.apache.commons.io.FileUtils.readLines(file, "UTF-8");
        list.forEach(line -> {
            String[] split = line.split("\t");

            if (split.length != 3) {
                //长度不为2 -> 异常
                throw new RuntimeException("文件格式存在问题");
            }
            String name = split[1];
            list4837.add(name);
        });

        file = new File("src/main/resources/data/核心指标/AllData4826");
        list = FileUtils.readLines(file, "UTF-8");
        list.forEach(line -> {
            String[] split = line.split("\t");
            if (split.length != 2) {
                throw new RuntimeException("文件结构出现问题");
            }
            String name = split[1];
            list4826.add(name);
        });
        list4837.addAll(list4826);
        return list4837;
    }

//
//    private void deal(Collection<Node> nodes) {
//        for (Node node : nodes) {
//            Map<String, List<Node>> map = new HashMap<>();
//            //收集data的节点
//            node.getChilds().forEach(child -> {
//                if (map.containsKey(child.getData())) {
//                    map.get(child.getData()).add(child);
//                } else {
//                    List<Node> tmp = new ArrayList<>();
//                    tmp.add(child);
//                    map.put(child.getData(), tmp);
//                }
//            });
//            List<Node> nodesToRemove = new ArrayList<>();
//            //筛选出需要移除的节点
//            map.forEach((key, nodeTmps) -> {
//                if (nodeTmps.size() >= 2) {
//
//                    int size = 0;
//                    for (Node nodeTmp : nodeTmps) {
//                        if (nodeTmp.toString().length() > size) {
//                            size = nodeTmp.toString().length();
//                        } else {
//                            nodesToRemove.add(nodeTmp);
//                        }
//                    }
//                }
//            });
//            node.getChilds().removeAll(nodesToRemove);
//            deal(node.getChilds());
//        }
//    }

}
