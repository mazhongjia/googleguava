package com.mzj.guava.cache.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 幻影引用示例
 *
 * 1、使用时必须与ReferenceQueue一起使用
 * 2、放进去，拿不出来
 *
 */
public class PhantomReferenceExample {
    public static void main(String[] args) throws InterruptedException {

        Ref ref = new Ref(10);

        ReferenceQueue<Ref> queue = new ReferenceQueue<>();
        PhantomReference<Ref> reference = new PhantomReference<>(ref,queue);
        ref = null;

        System.out.println(reference.get());//幻影引用：放进去拿不出来
    }
}

