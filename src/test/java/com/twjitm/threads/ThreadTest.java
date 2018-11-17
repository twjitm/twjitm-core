package com.twjitm.threads;

/**
 * Created by 文江 on 2018/5/21.
 */
public class ThreadTest implements Runnable {
    @Override
    public void run() {
        System.out.println("子线程开始执行");
        for (int i = 0; i < 10; i++) {
          /*  try {
               // Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            System.out.println(i);
        }
        System.out.println("子线程结束执行");
    }

    public static void main(String[] args) {
        System.out.println("主线程开始");
        ThreadTest threadTest = new ThreadTest();
        threadTest.run();
        Thread2 thread2 = new Thread2();
        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程结束");
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
            for (int i = 0; i < 5; i++) {
                System.out.println("子线程" + Thread.currentThread().getName() + "运行 : " + i);
                try {
                    sleep((int) Math.random() * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " 线程运行结束!");

        }
    }
}
