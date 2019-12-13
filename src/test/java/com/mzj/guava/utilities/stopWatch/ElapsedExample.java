package com.mzj.guava.utilities.stopWatch;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: mazhongjia
 * @Date: 2019/12/9 12:32
 * @Version: 1.0
 */
public class ElapsedExample {

    private static void process(String orderNo) throws InterruptedException {

        System.out.printf("start process the order %s\n",orderNo);
        long startNano = System.nanoTime();

        TimeUnit.SECONDS.sleep(1);

        System.out.printf("The orderNo %s process successful and elapsed %d\n",orderNo,(System.nanoTime() - startNano));

    }

    public static void main(String[] args) throws InterruptedException {
        process("123123");
    }
}
