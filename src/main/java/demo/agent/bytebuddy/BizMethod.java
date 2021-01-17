package demo.agent.bytebuddy;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;

@Slf4j
public class BizMethod {

    public String queryUserInfo(String uid, String token) throws
            InterruptedException {
        System.out.println("执行开始");
        Thread.sleep(new Random().nextInt(500));
        System.out.println("执行结束");
        return "德莱联盟，王牌工程师。小傅哥(公众号:bugstack虫洞栈)，申请出栈!";
    }



    @Test
    public void test_byteBuddy() throws Exception {
        // 加载类
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(BizMethod.class)
                .method(ElementMatchers.named("queryUserInfo"))
                .intercept(MethodDelegation.to(MonitorDemo.class))//委托给指定类
                .make();
        //保存
        dynamicType.saveIn(new File("./xxx"));//动态class保存到文件夹下
        // 反射调用
        Class<?> clazz = dynamicType.load(BizMethod.class.getClassLoader())
                .getLoaded();
        clazz.getMethod("queryUserInfo", String.class,
                String.class).invoke(clazz.newInstance(), "10001", "Adhl9dkl");
    }

}

