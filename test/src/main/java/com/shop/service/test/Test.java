package com.shop.service.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    //模拟数据库中的唯一属性
    static int count = 10;
    public static void main(String[] args) throws InterruptedException {
        //创建10容量的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //对10个线程做监控
        CountDownLatch countDownLatch = new CountDownLatch(10);
        //循环开启10个线程操作count属性
        for(int i = 0;i<10;i++){
            int finalI = i;
            executorService.execute(() -> {

                //执行扣减库存
                changeCount(finalI);
                //让线程监听程序挖成一个监听
                countDownLatch.countDown();

            });

        }
        //让后续代码等待10个线程执行完毕
        countDownLatch.await();
        //执行完毕之后关闭线程池
        executorService.shutdown();
        //输出最终得到的count
        System.out.println("最终count的结果为："+count);
    }
    //创建扣减库存所需操作的函数
    synchronized public static void changeCount(int finalI){
        //模拟查询count当前的值
        int c = count;
        //输出当前线程所获取的count结果
        System.out.println("第"+ finalI +"个线程获取的count的结果为："+c);
        //做库存的扣减，最终设置到count中
        count = c-1;
        //输出当前线程扣减之后的库存
        System.out.println("第"+ finalI +"个线程操作之后的count的结果为："+count);
    }
}