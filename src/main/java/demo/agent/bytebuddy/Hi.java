package demo.agent.bytebuddy;

import javassist.Modifier;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Hi {

    public static void main(String[] args) {
        System.out.println(Arrays.asList(args));
    }

    /**
     * 创建一个class
     * 覆写方法
     * 保存到文件夹
     */
    @Test
    public void one() throws IllegalAccessException, InstantiationException, IOException {
        DynamicType.Unloaded<Object> dynamicType = new ByteBuddy()
                .subclass(Object.class)//继承Object
                .name("bytebuddy.test.dynamicType.className1")//指定类名(不指定默认生成)
                .method(named("toString"))//指定方法
                .intercept(FixedValue.value("Hello World!"))//拦截设置返回值
                .make();
        //编译
        String helloWorld = dynamicType.load(Hi.class.getClassLoader())//指定类加载器
                .getLoaded()
                .newInstance()//新建实例
                .toString();
        System.out.println(helloWorld);  // Hello World!
        dynamicType.saveIn(new File("./xxx"));//动态class保存到文件夹下
    }

    /**
     * 创建一个class
     * 覆写方法
     * 保存到文件夹
     *
     * <pre>
     * package bytebuddy.test.dynamicType;
     *
     * import demo.agent.bytebuddy.Hi;
     *
     * public class className3 {
     *     public static void main(String[] args) {
     *         Hi.main(var0);
     *     }
     *
     *     public className3() {
     *     }
     * }
     * </pre>
     */
    @Test
    public void two() throws IllegalAccessException, InstantiationException, IOException {
        DynamicType.Unloaded<Object> dynamicType = new ByteBuddy()
                .subclass(Object.class)//继承Object
                .name("bytebuddy.test.dynamicType.className1")//指定类名(不指定默认生成)
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)//定位函数名称+返回值+修饰符
                .withParameter(String[].class, "args")//指定参数
                .intercept(FixedValue.value("Hello World!"))//拦截设置返回值
                .make();
        //编译
        String helloWorld = dynamicType.load(Hi.class.getClassLoader())//指定类加载器
                .getLoaded()
                .newInstance()//新建实例
                .toString();
        System.out.println(helloWorld);  // Hello World!
        dynamicType.saveIn(new File("./xxx"));//动态class保存到文件夹下
    }

    /**
     * 创建一个class
     * 覆写方法
     * 保存到文件夹
     *
     * <pre>
     * package bytebuddy.test.dynamicType;
     *
     * import demo.agent.bytebuddy.Hi;
     *
     * public class className3 {
     *     public static void main(String[] args) {
     *         Hi.main(var0);
     *     }
     *
     *     public className3() {
     *     }
     * }
     * </pre>
     */
    @Test
    public void three() throws IllegalAccessException, InstantiationException, IOException, NoSuchMethodException, InvocationTargetException {
        DynamicType.Unloaded<Object> dynamicType = new ByteBuddy()
                .subclass(Object.class)//继承Object
                .name("bytebuddy.test.dynamicType.className3")//指定类名(不指定默认生成)
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)//定位函数名称+返回值+修饰符
                .withParameter(String[].class, "args")//指定参数
                .intercept(MethodDelegation.to(Hi.class))//拦截设置委托！！！必须要对应上，不然会
                .make();
        //获取class来调用
        Class<?> aClass = dynamicType.load(Hi.class.getClassLoader())//指定类加载器
                .getLoaded();
        String[] args = {"hello word"};
        aClass.getMethod("main", String[].class).invoke(aClass.newInstance(), (Object) args);
        dynamicType.saveIn(new File("./xxx"));//动态class保存到文件夹下
    }

}
