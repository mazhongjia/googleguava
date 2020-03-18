package com.mzj.guava.io.closer;

import com.google.common.io.Closer;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CloserTest {

    /**
     * Guava Closer解决异常被压制问题：好处是：不会漏掉异常、影响分析问题
     *
     * 此方法仅仅用于演示，不能运行，main方法中代码可以运行
     *
     * @param args
     * @throws IOException
     */
    @Test
    public void test(String[] args) throws IOException {

        Closer closer = Closer.create();
        try {
            //将Closeable注册到Closer中，让其帮我们close
            BufferedReader br = closer.register(new BufferedReader(new FileReader("E:\\01.study\\03.google guava\\wamgwenjun\\other\\googleguava\\src\\main\\resources\\io\\source.txt")));
            br.readLine();
        } catch (IOException e) {
            throw closer.rethrow(e);//抛出原始异常
        } finally {
            closer.close();//调用register的Closeable的close();如果close过程出现异常则进行异常传递（追加）：原始异常+finally执行异常统一封装、全部抛出
        }
    }

    //===================下面所有代码演示了如果不使用Guava Closer，异常被压制的产生过程，如果解决（下面的解决过程就是Guava Closer实现）===================

    /**
     * 用于测试异常抛出、catch语句块、finally语句块执行顺序
     */
    @Test(expected = RuntimeException.class)
    public void testTryCatchFinally() {
        try {
            System.out.println("work area");
            throw new IllegalArgumentException();
        } catch (Exception e) {
            System.out.println("exception area");
        } finally {
            System.out.println("finally area");
        }
    }

    /**
     * 演示try...finally...机制，如果在正常语句抛出异常后执行finally过程再抛异常就会压制原异常的问题
     *
     * 执行：只看到异常2，异常1被压制了。。。。
     */
//    public static void main(String[] args) {
//        try {
//            throw new RuntimeException("1");
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            //模拟执行close时抛出异常
//            throw new RuntimeException("2");
//        }
//    }

    /**
     * 解决上述问题
     * <p>
     * 执行：看到1，2两个异常
     * <p>
     * 这种方式就是Guava Closer所解决的问题
     */
    public static void main(String[] args) {
        Throwable t = null;
        try {
            throw new RuntimeException("1");
        } catch (Exception e) {
            t = e;
            throw e;
        } finally {
            RuntimeException runtimeException = new RuntimeException("2");
            runtimeException.addSuppressed(t);//异常传递（追加）
            throw runtimeException;
        }
    }
}
