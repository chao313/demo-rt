//package demo.agent;
//
//import demo.agent.bytebuddy.MonitorTrack;
//import lombok.extern.slf4j.Slf4j;
//import net.bytebuddy.ByteBuddy;
//import net.bytebuddy.agent.builder.AgentBuilder;
//import net.bytebuddy.description.type.TypeDescription;
//import net.bytebuddy.dynamic.DynamicType;
//import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
//import net.bytebuddy.implementation.MethodDelegation;
//import net.bytebuddy.matcher.ElementMatchers;
//import net.bytebuddy.utility.JavaModule;
//
//import java.lang.instrument.Instrumentation;
//
///**
// * 代理 -> 添加的是代码逻辑。只会运行一次(每个class只会加载一次)
// */
//@Slf4j
//public class AgentMainbak {
//    /**
//     * @param args args[0]:packageName
//     * @param inst
//     */
//    public static void premain(String args, Instrumentation inst) {
//      if (StringUtils.isBlank(args)) {
//            throw new RuntimeException("指定参数为空,程序退出");
//        }
//        String packageName = args;
//        buildAgent(inst, packageName);
//    }
//
//    /**
//     * @param inst
//     * @param packageName 需要代理的包名
//     */
//    private static void buildAgent(Instrumentation inst, String packageName) {
//        ByteBuddy byteBuddy = new ByteBuddy();
//        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
//            @Override
//            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
////                byteBuddy.redefine(typeDescription, ClassFileLocator.ForClassLoader.ofBootLoader());
//
//                DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition<?> intercept = builder
//                        .method(ElementMatchers.not(ElementMatchers.named("toString"))
//                                .and(ElementMatchers.not(ElementMatchers.named("hashCode")))
//                        ) // 拦截任意方法
//                        .intercept(MethodDelegation.to(MonitorTrack.class));// 委托
//
//                intercept.make().load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION);
//                return intercept;
//            }
//        };
//
//        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
//            @Override
//            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
////                log.info("s:{}", s);
//            }
//
//            @Override
//            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
////                log.info("s:{}");
//            }
//
//            @Override
//            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {
////                log.info("s:{}");
//            }
//
//            @Override
//            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
////                log.info("s:{}");
//            }
//
//            @Override
//            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
////                log.info("s:{}");
//            }
//        };
//
//        new AgentBuilder
//                .Default(byteBuddy)
//                .type(ElementMatchers.nameStartsWith(packageName)) // 指定需要拦 截的类
//                .transform(transformer)
//                .with(listener)
//                .installOn(inst);
//    }
//
//}
//
