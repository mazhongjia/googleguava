package com.mzj.guava.concurrent.ratelimiter.tokenBucket;

public class TokenBucketExample {
    public static void main(String[] args) {
        final TokenBucket tokenBucket = new TokenBucket();
        for (int i=0;i<200;i++){
            new Thread(tokenBucket::buy).start();
        }
    }
}
