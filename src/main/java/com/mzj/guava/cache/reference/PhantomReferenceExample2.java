package com.mzj.guava.cache.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 幻影引用示例2（设置：JVM启动参数：-Xmx128M -Xms64M -XX:+PrintGCDetails）
 *
 * 1、使用时必须与ReferenceQueue一起使用
 * 2、放进去，拿不出来
 *
 */
public class PhantomReferenceExample2 {
    public static void main(String[] args) throws InterruptedException {

        Ref ref = new Ref(10);

        ReferenceQueue<Ref> queue = new ReferenceQueue<>();
        MyPhantomReference<Ref> reference = new MyPhantomReference<>(ref,queue,10);
        ref = null;

        System.out.println(reference.get());//幻影引用：放进去拿不出来，输出null

        System.gc();//执行一次gc，如果幻影引用内部对象被回收，会输出：The index [10] will be GC....但是！！执行finallize方法，对象并不一定马上被GC，这里如果想看测试结果，需要借助jconsole等工具，手工执行GC

        MyPhantomReference<? extends Ref> object = (MyPhantomReference<? extends Ref>) queue.remove();//幻影引用对象被回收，remove方法会从阻塞中返回

        System.out.println(object);//但是这里拿到的是PhantomReference，而内部的ref无法通过PhantomReference拿到，因为是幻影引用

        object.doAction(); //需要将追踪对象的信息保存在自定义的PhantomReference中
    }

    private static class MyPhantomReference<T> extends PhantomReference<T>{

        private int index;

        public MyPhantomReference(T referent, ReferenceQueue<T> q,int index) {
            super(referent, q);
            this.index = index;
        }

        public void doAction(){
            System.out.println("The object " + index +" is GC . ");
        }
    }
}
