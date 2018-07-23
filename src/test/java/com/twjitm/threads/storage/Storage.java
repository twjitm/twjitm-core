package com.twjitm.threads.storage;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 文江 on 2018/5/22
 * .采用wait/notify机制实现生产者和消费者模式
 */
public class Storage {
    private static int maz_size = 100;
    private  final  LinkedList <Object> list = new LinkedList<>();

    //---------------------------------------------------------------------------第一中方式
    public void produceofwn(int num) {
        synchronized (list) {
            while ((list.size() + num) > maz_size) {
                System.out.println("生产线。。容量不足，等待中。。。。。");
                try {
                    list.wait();
                    System.out.println("produce被唤醒，请继续。。。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < num; i++) {
                System.out.println("容量" + list.size() + "可用。正在生产");
                list.add(new Object());
                list.notifyAll();
            }
        }
    }

    //
    public void consumeofwn(int num) {
        synchronized (list) {
            while (list.size() < num) {
                System.out.println("消费线" + list.size() + "不够所需。。。等待中");
                try {
                    list.wait();
                    System.out.println("concum被唤醒，请继续。。。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < num; i++) {
                list.remove();
                System.out.println("消费了第" + i + "个");
            }
            list.notifyAll();
        }
    }

    //---------------------------------------------------------------------------------第二种方式
    private Lock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();

    public void produceOfLock(int num) {
        lock.lock();
        while ((list.size() + num) > maz_size) {
            System.out.println("仓库容量不够，无法生成，等待中。。。" + list.size());
            try {
                full.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i <= num; i++) {
            list.add(new Object());
            System.out.println("生产了这么多"+i);
        }
        full.signal();
        empty.signal();
        lock.unlock();
    }

    public void consumeOfLock(int num) {
        lock.lock();
        while (list.size() < num) {
            System.out.println("所需消费不足" + list.size());
            try {
                empty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < num ; i++) {
            System.out.println("消费了" + i);
            list.remove();
        }
        full.signal();
        empty.signal();
        lock.unlock();

    }
    //----------------------------------------------------第三种
    private LinkedBlockingQueue<Object> linkedBlockingQueue=new LinkedBlockingQueue<>(100);

    public void produceOfBlock(int num){
        if(linkedBlockingQueue.size()==maz_size){
            System.out.println("仓库容量已满");
        }
        for(int i=0;i<num;i++){
            try {
                System.out.println("生成了产品"+i);
                linkedBlockingQueue.put(new Object());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void consumeOfBlock(int num){
        if(linkedBlockingQueue.size()==0){
            System.out.println("容量为0");
        }

        for(int i=0;i<num;i++){
            try {
                linkedBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String arg[]) {
        /*Storage storage = new Storage();
        Product produce = new Product(storage, 120);
        Product produce1 = new Product(storage, 10);
        Product produce2 = new Product(storage, 30);
        Consume consume = new Consume(storage, 20);
        Consume consume1 = new Consume(storage, 10);
        produce.start();
        produce1.start();
        produce2.start();
        consume.start();
        consume1.start();*/
        ExecutorService executorService= Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            try {
                System.out.println("22222222222");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("-------------");
        executorService.execute(() -> {
            try {
                System.out.println("1111111111");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("++++++++++");
        executorService.submit(() -> {
            System.out.println("33333333333");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("............");
        executorService.shutdown();
        Runtime runtime=Runtime.getRuntime();
        System.out.println(runtime.totalMemory()/1024*1024);
         int  a[]=new int[5];
            a[0]=1;

    }


}
