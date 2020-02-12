package com.mzj.guava.cache.reference;

import java.lang.ref.WeakReference;

/**
 * 弱引用示例2（设置：JVM启动参数：-Xmx128M -Xms64M -XX:+PrintGCDetails）
 *
 *不论是Minor GC、Major GC和Full GC(只要是执行GC，包括代码调用System.gc())都回收弱引用
 */
public class WeakReferenceExample2 {
    public static void main(String[] args) throws InterruptedException {
        Ref ref = new Ref(10);

        WeakReference<Ref> reference = new WeakReference<>(ref);

        ref = null;

        System.out.println(reference.get());
        System.gc();
        System.out.println(reference.get());//执行结果为null
    }
}
