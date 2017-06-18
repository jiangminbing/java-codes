package pcmodel;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by 李恒名 on 2017/6/8.
 *
 *  生产者消费者模型测试
 */
public class Tester {
    public static void main(String[] args) {
        test1();
    }

    //测试wait、notify实现
    public static void test1(){
        List<Product> buffer = new LinkedList<Product>();
        ExecutorService es = Executors.newFixedThreadPool(5);
        //两个生产者
        es.execute(new Producer(buffer));
        es.execute(new Producer(buffer));
        //三个消费者
        Consumer consumer = new Consumer(buffer);
        es.execute(new Consumer(buffer));
        es.execute(new Consumer(buffer));
        es.execute(new Consumer(buffer));

        es.shutdown();
        /**
         输出：
         ...
         生产者[pool-1-thread-2]生产了一个产品：iPhone 手机
         生产者[pool-1-thread-2]生产了一个产品：iPhone 手机
         消费者[pool-1-thread-5]消费了一个产品：iPhone 手机
         消费者[pool-1-thread-5]消费了一个产品：iPhone 手机
         消费者[pool-1-thread-5]消费了一个产品：iPhone 手机
         消费者[pool-1-thread-5]消费了一个产品：iPhone 手机
         消费者[pool-1-thread-5]消费了一个产品：iPhone 手机
         生产者[pool-1-thread-1]生产了一个产品：iPhone 手机
         消费者[pool-1-thread-4]消费了一个产品：iPhone 手机
         生产者[pool-1-thread-2]生产了一个产品：iPhone 手机
         ...
         */

    }
    //测试blocked queue实现
    public static void test2(){
        BlockingQueue<Product> buffer = new LinkedBlockingQueue<Product>(10);
        ExecutorService es = Executors.newFixedThreadPool(5);
        //两个生产者
        es.execute(new ProducerBlockedQueueImpl(buffer));
        es.execute(new ProducerBlockedQueueImpl(buffer));
        //三个消费者
        es.execute(new ConsumerBlockedQueueImpl(buffer));
        es.execute(new ConsumerBlockedQueueImpl(buffer));
        es.execute(new ConsumerBlockedQueueImpl(buffer));

        es.shutdown();

        /**
         输出：
         ...
         生产者[pool-1-thread-1]生产了一个产品：MAC
         消费者[pool-1-thread-3]消费了一个产品：MAC
         消费者[pool-1-thread-4]消费了一个产品：MAC
         消费者[pool-1-thread-4]消费了一个产品：MAC
         消费者[pool-1-thread-4]消费了一个产品：MAC
         生产者[pool-1-thread-2]生产了一个产品：MAC
         生产者[pool-1-thread-2]生产了一个产品：MAC
         生产者[pool-1-thread-2]生产了一个产品：MAC
         ...
         */
    }
}
