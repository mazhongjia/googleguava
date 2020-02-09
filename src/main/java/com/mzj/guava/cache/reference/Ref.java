package com.mzj.guava.cache.reference;

/**
 * 引用示例对象
 */
public class Ref {

    /**
     * 1个对象占用1兆大小
     */
    private byte[] data = new byte[1024 * 1024];
    private final int index;

    public Ref(int index) {
        this.index = index;
    }

    /**
     * JVM在进行垃圾回收时调用finalize方法
     * <p>
     * 实现上：第一次回收：JVM在回收对象时首先会给这个对象打个回收标记，并且调用finalize，并不立即回收
     * 第二次回收：直接回收，不会再次调用finalize方法
     *
     * 一个对象被判定符合GC条件以后，在GC之前finalize（）方法会被调用，所以基本可以说其被回收了。 
     * 但是有种特殊情况：就是在finalize（）中重新让对象不符合GC条件，这样对象就避开了一次GC，这时对象就没有被回收，下次对象再次符合条件的时候finalize（）也不会被调用，但是它还是被回收了 
     * ps：finalize（）只能被调用一次
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
//        super.finalize();
        System.out.println("The index [" + index + "] will be GC ." );
    }
}
