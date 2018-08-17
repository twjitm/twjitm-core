package com.twjitm.threads.storage;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by �Ľ� on 2018/5/22
 * .����wait/notify����ʵ�������ߺ�������ģʽ
 */
public class Storage {
    private static int maz_size = 100;
    private  final  LinkedList <Object> list = new LinkedList<>();

    //---------------------------------------------------------------------------��һ�з�ʽ
    public void produceofwn(int num) {
        synchronized (list) {
            while ((list.size() + num) > maz_size) {
                System.out.println("�����ߡ����������㣬�ȴ��С���������");
                try {
                    list.wait();
                    System.out.println("produce�����ѣ��������������");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < num; i++) {
                System.out.println("����" + list.size() + "���á���������");
                list.add(new Object());
                list.notifyAll();
            }
        }
    }

    //
    public void consumeofwn(int num) {
        synchronized (list) {
            while (list.size() < num) {
                System.out.println("������" + list.size() + "�������衣�����ȴ���");
                try {
                    list.wait();
                    System.out.println("concum�����ѣ��������������");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < num; i++) {
                list.remove();
                System.out.println("�����˵�" + i + "��");
            }
            list.notifyAll();
        }
    }

    //---------------------------------------------------------------------------------�ڶ��ַ�ʽ
    private Lock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();

    public void produceOfLock(int num) {
        lock.lock();
        while ((list.size() + num) > maz_size) {
            System.out.println("�ֿ������������޷����ɣ��ȴ��С�����" + list.size());
            try {
                full.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i <= num; i++) {
            list.add(new Object());
            System.out.println("��������ô��"+i);
        }
        full.signal();
        empty.signal();
        lock.unlock();
    }

    public void consumeOfLock(int num) {
        lock.lock();
        while (list.size() < num) {
            System.out.println("�������Ѳ���" + list.size());
            try {
                empty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < num ; i++) {
            System.out.println("������" + i);
            list.remove();
        }
        full.signal();
        empty.signal();
        lock.unlock();

    }
    //----------------------------------------------------������
    private LinkedBlockingQueue<Object> linkedBlockingQueue=new LinkedBlockingQueue<>(100);

    public void produceOfBlock(int num){
        if(linkedBlockingQueue.size()==maz_size){
            System.out.println("�ֿ���������");
        }
        for(int i=0;i<num;i++){
            try {
                System.out.println("�����˲�Ʒ"+i);
                linkedBlockingQueue.put(new Object());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void consumeOfBlock(int num){
        if(linkedBlockingQueue.size()==0){
            System.out.println("����Ϊ0");
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
        produce.bootstrap();
        produce1.bootstrap();
        produce2.bootstrap();
        consume.bootstrap();
        consume1.bootstrap();*/
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
