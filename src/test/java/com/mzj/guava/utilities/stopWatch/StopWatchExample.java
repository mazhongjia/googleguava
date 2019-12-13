package com.mzj.guava.utilities.stopWatch;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @Auther: mazhongjia
 * @Date: 2019/12/9 12:46
 * @Version: 1.0
 */
public class StopWatchExample {

    private static void process(String orderNo) throws InterruptedException {

        System.out.printf("start process the order %s\n",orderNo);
        Stopwatch stopwatch = Stopwatch.createStarted();

        TimeUnit.SECONDS.sleep(1);

        System.out.printf("The orderNo %s process successful and elapsed %s\n",orderNo,stopwatch.stop().elapsed());

    }

    public static void main(String[] args) throws InterruptedException {
        process("123123");
    }
}
