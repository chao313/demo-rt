package demo.agent;

import demo.agent.bytebuddy.MonitorTrack;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * 代理 -> 添加的是代码逻辑。只会运行一次(每个class只会加载一次)
 */
@Slf4j
public class AgentMain {
    /**
     * @param args args[0]:packageName
     * @param inst
     */
    public static void premain(String args, Instrumentation inst) {
        String packageName = args;
        buildAgent(inst, packageName);
    }

    /**
     * @param inst
     * @param packageName 需要代理的包名
     */
    private static void buildAgent(Instrumentation inst, String packageName) {
        AgentBuilder.Transformer transformer = (builder, typeDescription,
                                                classLoader, javaModule) -> {
            return builder
                    .method(ElementMatchers.not(ElementMatchers.named("toString"))
                            .and(ElementMatchers.not(ElementMatchers.named("hashCode")))
                    ) // 拦截任意方法
                    .intercept(MethodDelegation.to(MonitorTrack.class));// 委托
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
//                log.info("s:{}", s);
            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
//                log.info("s:{}");
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {
//                log.info("s:{}");
            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
//                log.info("s:{}");
            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
//                log.info("s:{}");
            }
        };

        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith(packageName)) // 指定需要拦 截的类
                .transform(transformer)
                .with(listener)
                .installOn(inst);
    }

}

