package com.mzj.guava.concurrent.future;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletableFutureExample {

        private static Logger LOG = LoggerFactory.getLogger(CompletableFutureExample.class);

        @Test
        public void testCompletableFuture() throws Exception {
            // case1: supplyAsync
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                LOG.info("Run supplyAsync.");
                return "Return result of Supply Async";
            });

            // case2: thenRun，与supplyAsync同线程
            future.thenRun(new Runnable() {

                @Override
                public void run() {
                    LOG.info("Run action.");
                }
            });

            // case2: thenRunAsync，另启动线程执行
            future.thenRunAsync(new Runnable() {

                @Override
                public void run() {
                    LOG.info("Run async action.");
                }
            });

            // 主动触发Complete结束方法
            // future.complete("Manual complete value.");
            future.whenComplete((v, e) -> {
                LOG.info("WhenComplete value: " + v);
                LOG.info("WhenComplete exception: " + e);
            });
            CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
                LOG.info("Return result of Run Async.");
            });

            CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
                return "hello";
            });
            CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> {
                return "world";
            });
            CompletableFuture<String> f = future3.thenCombine(future4,
                    (x, y) -> x + "-" + y);
            LOG.info(f.get());
        }
}
