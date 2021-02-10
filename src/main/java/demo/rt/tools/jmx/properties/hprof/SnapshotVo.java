package demo.rt.tools.jmx.properties.hprof;


import com.sun.tools.hat.internal.model.*;
import demo.rt.util.ReflectUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.ref.SoftReference;
import java.util.*;

@Slf4j
@Data
public class SnapshotVo {
    private Hashtable<Number, JavaHeapObject> idToHeapObjects;//全部实例(idToObject)
    private Map<String, JavaClass> idToClasses;//全部Class(idToClasses)
    private Root[] roots;//GC回收根节点
    private JavaClass[] classes;//类
    //下面可能没有用
    private Hashtable<Number, JavaClass> fakeClasses;//伪造Class
    private Map<JavaHeapObject, Boolean> newObjects;
    private Map<JavaHeapObject, StackTrace> siteTraces;//对象2追踪
    private Map<JavaHeapObject, Root> rootsMap;//对象2root
    private SoftReference<Vector> finalizablesCache;

    //衍生
    private Map<JavaHeapObject, Integer> javaHeapObjectToSizeSort;


    public static SnapshotVo builder(Snapshot snapshot) throws NoSuchFieldException, IllegalAccessException {
        SnapshotVo vo = new SnapshotVo();
        vo.idToHeapObjects = ReflectUtil.getFieldValue(snapshot, "heapObjects");//全部实例(idToObject)
        vo.roots = snapshot.getRootsArray();//GC回收根节点
        vo.classes = snapshot.getClassesArray();//全部的类
        vo.idToClasses = ReflectUtil.getFieldValue(snapshot, "classes");//全部实例(idToClasses)
        vo.fakeClasses = ReflectUtil.getFieldValue(snapshot, "fakeClasses");
        vo.newObjects = ReflectUtil.getFieldValue(snapshot, "newObjects");
        vo.siteTraces = ReflectUtil.getFieldValue(snapshot, "siteTraces");
        vo.rootsMap = ReflectUtil.getFieldValue(snapshot, "rootsMap");
        vo.finalizablesCache = ReflectUtil.getFieldValue(snapshot, "finalizablesCache");
        //衍生
        Category category = builderToCategory(vo.idToHeapObjects);
        Map<String, JavaClass> stringJavaClassMap = category.generateMapClass();
        Map<Class, Collection<JavaObject>> classCollectionMap = category.generateMapObject();
        JavaClass javaClass = stringJavaClassMap.get("java.lang.System");
        Enumeration instances = javaClass.getInstances(true);
//        vo.javaHeapObjectToSizeSort = builder(vo.idToHeapObjects);
        return vo;
    }


    public static Map<JavaHeapObject, Integer> builder(Hashtable<Number, JavaHeapObject> idToHeapObjects) {
        Map<JavaHeapObject, Integer> result = new TreeMap<>(new Comparator<JavaHeapObject>() {
            @Override
            public int compare(JavaHeapObject o1, JavaHeapObject o2) {
                return o1.getSize() - o2.getSize();
            }
        });
        for (Map.Entry<Number, JavaHeapObject> entry : idToHeapObjects.entrySet()) {
            Number number = entry.getKey();
            JavaHeapObject javaHeapObject = entry.getValue();
            try {
                //存在四种  com.sun.tools.hat.internal.model.JavaClass
                //存在四种  com.sun.tools.hat.internal.model.JavaObject
                //存在四种  com.sun.tools.hat.internal.model.JavaObjectArray
                //存在四种  com.sun.tools.hat.internal.model.JavaValueArray

                Class<? extends JavaHeapObject> aClass = javaHeapObject.getClass();
                if (aClass == JavaClass.class) {
                    result = dealJavaClass(result, javaHeapObject);
                } else if (aClass == JavaObject.class) {
                    result = dealJavaObject(result, javaHeapObject);
                } else if (aClass == JavaObjectArray.class) {
                    result = dealJavaObjectArray(result, javaHeapObject);
                } else if (aClass == JavaValueArray.class) {
                    result = dealJavaValueArray(result, javaHeapObject);
                } else {
                    log.info("出现预期以外的类型:{}", aClass);
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    javaHeapObject.getSize();
                } catch (Exception e2) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }


    private static Map<JavaHeapObject, Integer> dealJavaClass(Map<JavaHeapObject, Integer> result, JavaHeapObject javaHeapObject) throws NoSuchFieldException, IllegalAccessException {
        Object clazz = ReflectUtil.getFieldValue(javaHeapObject, "clazz");
        if (clazz instanceof JavaClass) {
            result.put(javaHeapObject, javaHeapObject.getSize());
        } else {
            log.info("跳过:{}");
        }
        return result;
    }

    private static Map<JavaHeapObject, Integer> dealJavaObject(Map<JavaHeapObject, Integer> result, JavaHeapObject javaHeapObject) throws NoSuchFieldException, IllegalAccessException {
        Object clazz = ReflectUtil.getFieldValue(javaHeapObject, "clazz");
        if (clazz instanceof JavaClass) {
            result.put(javaHeapObject, javaHeapObject.getSize());
        } else {
            log.info("跳过:{}");
        }
        return result;
    }

    private static Map<JavaHeapObject, Integer> dealJavaObjectArray(Map<JavaHeapObject, Integer> result, JavaHeapObject javaHeapObject) throws NoSuchFieldException, IllegalAccessException {
        Object clazz = ReflectUtil.getFieldValue(javaHeapObject, "clazz");
        if (clazz instanceof JavaClass) {
            result.put(javaHeapObject, javaHeapObject.getSize());
        } else {
            log.info("跳过:{}");
        }
        return result;
    }

    private static Map<JavaHeapObject, Integer> dealJavaValueArray(Map<JavaHeapObject, Integer> result, JavaHeapObject javaHeapObject) throws NoSuchFieldException, IllegalAccessException {
        Object clazz = ReflectUtil.getFieldValue(javaHeapObject, "clazz");
        if (clazz instanceof JavaClass) {
            result.put(javaHeapObject, javaHeapObject.getSize());
        } else {
            log.info("跳过:{}");
        }
        return result;
    }


    /**
     * 分类JavaHeapObject
     */
    private static Category builderToCategory(Hashtable<Number, JavaHeapObject> idToHeapObjects) {
        Category category = new Category();
        for (Map.Entry<Number, JavaHeapObject> entry : idToHeapObjects.entrySet()) {
            Number number = entry.getKey();
            JavaHeapObject javaHeapObject = entry.getValue();
            Class<? extends JavaHeapObject> aClass = javaHeapObject.getClass();
            if (aClass == JavaClass.class) {
                JavaClass javaClass = (JavaClass) javaHeapObject;
                category.getJavaClassCollection().add(javaClass);
            } else if (aClass == JavaObject.class) {
                JavaObject javaObject = (JavaObject) javaHeapObject;
                category.getJavaObjectCollection().add(javaObject);
            } else if (aClass == JavaObjectArray.class) {
                JavaObjectArray javaObjectArray = (JavaObjectArray) javaHeapObject;
                category.getJavaObjectArrayCollection().add(javaObjectArray);
            } else if (aClass == JavaValueArray.class) {
                JavaValueArray javaValueArray = (JavaValueArray) javaHeapObject;
                category.getJavaValueArrayCollection().add(javaValueArray);
            } else {
                log.info("出现预期以外的类型:{}", aClass);
            }
        }
        return category;
    }

    @Data
    static
    public class Category {
        Collection<JavaClass> JavaClassCollection = new ArrayList<>();
        Collection<JavaObject> javaObjectCollection = new ArrayList<>();
        Collection<JavaObjectArray> javaObjectArrayCollection = new ArrayList<>();
        Collection<JavaValueArray> javaValueArrayCollection = new ArrayList<>();

        /**
         * 根据name进行分类
         *
         * @return
         */
        public Map<String, JavaClass> generateMapClass() {
            Map<String, JavaClass> result = new HashMap<>();
            JavaClassCollection.forEach(javaClass -> {
                result.put(javaClass.getName(), javaClass);
            });
            return result;
        }

        /**
         * 获取
         *
         * @return
         */
        public Map<Class, Collection<JavaObject>> generateMapObject() throws NoSuchFieldException, IllegalAccessException {
            Map<Class, Collection<JavaObject>> result = new HashMap<>();
            for (JavaObject javaObject : javaObjectCollection) {
                Object clazz = ReflectUtil.getFieldValue(javaObject, "clazz");
                if (result.containsKey(clazz.getClass())) {
                    result.get(clazz.getClass()).add(javaObject);
                } else {
                    Collection<JavaObject> tmp = new ArrayList<>();
                    tmp.add(javaObject);
                    result.put(clazz.getClass(), tmp);
                }
            }
            return result;
        }
    }

}
