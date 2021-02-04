# 用于学习 ElasticSearch 的 demo

[swagger](http://127.0.0.1:9001/demo_rt/swagger-ui.html)

http://localhost:8080/#/GraphTrackController

d3:
https://observablehq.com/@d3/gallery

https://observablehq.com/@d3/collapsible-tree

relation-graph:
http://www.relation-graph.com/#/docs/graph - 关键示意
http://www.relation-graph.com/#/docs/start
https://github.com/seeksdream/relation-graph

SpringBoot JMX
https://blog.csdn.net/weixin_34024034/article/details/93176218?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control


## =后面加上参数
-javaagent:/Users/chao/IdeaWorkspace/demo-rt.rt/target/demo-rt-0.0.1-SNAPSHOT.jar=demo
-javaagent:C:\import\work_git_svn\demo-rt\target\demo-rt-0.0.1-SNAPSHOT.jar=demo

<dependency>
  <groupId>net.bytebuddy</groupId>
  <artifactId>byte-buddy</artifactId>
  <version>1.10.19</version>
</dependency>

## 如何学习一个类?
* 找到有价值的类
* 学习上层接口+类(学到抽象类为止?接口不存在意义?)
* 学习类的结构(数组/内部类...)
* 画出图？
  * 每一个关键点画出图?分散?


* CAS
* SpringCloud和Dubbo的优劣
  1.协议不同 dubbo rpc 2.cloud是RestFull
* Dubbo的注册中心金额SpringCloud的注册中心的区别
  C(一致性)、A(可用性)和P(分区容错性)
  Zookeeper保证CP -> ZK会因为节点的不一致的失败
  Eureka保证AP    -> Eureka当节点不一致任可以正常工作
* HashMap的内部结构
* 阻塞队列的原理
* 线程池的用法和原理
* mysql的优化(explain的各种字段)
* 垃圾收集器的细节(CMS...)
  Serial收集器（复制算法): 新生代单线程收集器，标记和清理都是单线程，优点是简单高效；
  ParNew收集器 (复制算法): 新生代收并行集器，实际上是Serial收集器的多线程版本，在多核CPU环境下有着比Serial更好的表现；
  Parallel Scavenge收集器 (复制算法): 新生代并行收集器，追求高吞吐量，高效利用 CPU。吞吐量 = 用户线程时间/(用户线程时间+GC线程时间)，高吞吐量可以高效率的利用CPU时间，尽快完成程序的运算任务，适合后台应用等对交互相应要求不高的场景；
  Serial Old收集器 (标记-整理算法): 老年代单线程收集器，Serial收集器的老年代版本；
  Parallel Old收集器 (标记-整理算法)： 老年代并行收集器，吞吐量优先，Parallel Scavenge收集器的老年代版本；
  CMS(Concurrent Mark Sweep)收集器（标记-清除算法）： 老年代并行收集器，以获取最短回收停顿时间为目标的收集器，具有高并发、低停顿的特点，追求最短GC回收停顿时间。
  G1(Garbage First)收集器 (标记-整理算法)： Java堆并行收集器，G1收集器是JDK1.7提供的一个新收集器，G1收集器基于“标记-整理”算法实现，也就是说不会产生内存碎片。此外，G1收集器不同于之前的收集器的一个重要特点是：G1回收的范围是整个Java堆(包括新生代，老年代)，而前六种收集器回收的范围仅限于新生代或老年代。
* 死锁的原理和如何预防死锁
  互斥条件：一个资源每次只能被一个进程使用，即在一段时间内某 资源仅为一个进程所占有。此时若有其他进程请求该资源，则请求进程只能等待。
  请求与保持条件：进程已经保持了至少一个资源，但又提出了新的资源请求，而该资源 已被其他进程占有，此时请求进程被阻塞，但对自己已获得的资源保持不放。
  不可剥夺条件:进程所获得的资源在未使用完毕之前，不能被其他进程强行夺走，即只能 由获得该资源的进程自己来释放（只能是主动释放)。
  循环等待条件: 若干进程间形成首尾相接循环等待资源的关系

  预防死锁:
  破坏“不可剥夺”条件：一个进程不能获得所需要的全部资源时便处于等待状态，等待期间他占有的资源将被隐式的释放重新加入到 系统的资源列表中，可以被其他的进程使用，而等待的进程只有重新获得自己原有的资源以及新申请的资源才可以重新启动，执行。
  破坏”请求与保持条件“：第一种方法静态分配即每个进程在开始执行时就申请他所需要的全部资源。第二种是动态分配即每个进程在申请所需要的资源时他本身不占用系统资源。
  破坏“循环等待”条件：采用资源有序分配其基本思想是将系统中的所有资源顺序编号，将紧缺的，稀少的采用较大的编号，在申请资源时必须按照编号的顺序进行，一个进程只有获得较小编号的进程才能申请较大编号的进程

* 线程的各种状态
  1. 新建(NEW)：新创建了一个线程对象。
  2. 可运行(RUNNABLE)：线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中，获取cpu 的使用权 。
  3. 运行(RUNNING)：可运行状态(runnable)的线程获得了cpu 时间片（timeslice） ，执行程序代码。
  4. 阻塞(BLOCKED)：阻塞状态是指线程因为某种原因放弃了cpu 使用权，也即让出了cpu timeslice，暂时停止运行。直到线程进入可运行(runnable)状态，才有机会再次获得cpu timeslice 转到运行(running)状态。阻塞的情况分三种：
   4.1等待阻塞：运行(running)的线程执行o.wait()方法，JVM会把该线程放入等待队列(waitting queue)中。
   4.2同步阻塞：运行(running)的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入锁池(lock pool)中。
   4.3其他阻塞：运行(running)的线程执行Thread.sleep(long ms)或t.join()方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入可运行(runnable)状态。
  5. 死亡(DEAD)：线程run()、main() 方法执行结束，或者因异常退出了run()方法，则该线程结束生命周期。死亡的线程不可再次复生。
* cglib和java自带的优劣
  java动态代理是利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。被代理的对象必须要实现接口
  而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。因为采用的是继承，所以不能对final修饰的类进行代理
  从执行效率上看，Cglib动态代理效率较高。
* redis原子性的原因和常用集合
  字符串、哈希结构、列表、集合、可排序集合和基数
  Redis的操作之所以是原子性的，是因为Redis是单线程的。