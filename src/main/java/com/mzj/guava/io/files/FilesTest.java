package com.mzj.guava.io.files;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Guava的Files
 *
 * @Auther: mazhongjia
 * @Version: 1.0
 */
public class FilesTest {

    private final String SOURCE_FILE = "E:\\01.study\\03.google guava\\wamgwenjun\\other\\googleguava\\src\\main\\resources\\io\\source.txt";
    private final String SOURCE_FILE1 = "E:\\01.study\\03.google guava\\wamgwenjun\\other\\googleguava\\src\\main\\resources\\io\\source1.txt";
    private final String TARGET_FILE = "E:\\01.study\\03.google guava\\wamgwenjun\\other\\googleguava\\src\\main\\resources\\io\\source234.txt";

    /**
     * 拷贝文件A内容到文件B
     *
     * @throws IOException
     */
    @Test
    public void testCopyFilesWithGuava() throws IOException {
        File targetFile = new File(TARGET_FILE);
        Files.copy(new File(SOURCE_FILE),targetFile);
        assertThat(targetFile.exists(),equalTo(true));
    }

    /**
     * 使用JAVA nio2.0实现拷贝文件A内容到文件B
     *
     * @throws IOException
     */
    @Test
    public void testCopyFilesWithJDKNio2() throws IOException {
        java.nio.file.Files.copy(
                Paths.get("E:\\01.study\\03.google guava\\wamgwenjun\\other\\googleguava\\src\\main\\resources","io","source.txt"),
                Paths.get("E:\\01.study\\03.google guava\\wamgwenjun\\other\\googleguava\\src\\main\\resources","io","target.txt"),
                StandardCopyOption.REPLACE_EXISTING
        );
    }

    /**
     * 移动文件
     *
     * @throws IOException
     */
    @Test
    public void testMoveFile() throws IOException {
        File targetFile = new File(TARGET_FILE);
        Files.move(new File(SOURCE_FILE),targetFile);
        Files.move(targetFile,new File(SOURCE_FILE));
    }

    /**
     * 将一个文件读成String
     *
     * @throws IOException
     */
    @Test
    public void testToString() throws IOException {
        String expectedString = "today we will share the guava io knowledge.\n" +
                "\n" +
                "but only for the basic uage. if you wanted to get the more details.\n" +
                "\n" +
                "please read the guava document or source\n" +
                "\n" +
                "the guava source code is very cleanly and nice.";

        List<String> strings = Files.readLines(new File(SOURCE_FILE), Charsets.UTF_8);
        String result = Joiner.on("\n").join(strings);//连接集合中的内容，通过\n连接
        assertThat(result,equalTo(expectedString));
    }

    /**
     * 对文件中每一行通过LineProcessor进行处理（全读出来（Files.asCharSource）后进行处理：readLines(lineProcessor)）
     *
     * @throws IOException
     */
    @Test
    public void testToProcessString() throws IOException {

        /**
         * 获取SOURCE_FILE每行长度集合
         */
        LineProcessor<List<Integer>> lineProcessor = new LineProcessor<List<Integer>>() {

            private final List<Integer> lengthList = new ArrayList<>();

            @Override
            public boolean processLine(String line) throws IOException {
                //如果返回false，则不会继续读取
                lengthList.add(line.length());
                return true;
            }

            @Override
            public List<Integer> getResult() {
                return lengthList;
            }
        };
        List<Integer> result =  Files.asCharSource(new File(SOURCE_FILE), Charsets.UTF_8). readLines(lineProcessor);
        System.out.println(result);
    }

    /**
     * 计算一个文件的MD5、SHA1、SHA256值
     *
     */
    @Test
    public void testFileMD5() throws IOException {
        File file = new File(SOURCE_FILE);
        HashCode hashCode = Files.asByteSource(file).hash(Hashing.md5());
        System.out.println(hashCode);

        HashCode hashCode2 = Files.asByteSource(file).hash(Hashing.sha256());
        System.out.println(hashCode2);

        HashCode hashCode3 = Files.asByteSource(file).hash(Hashing.sha1());
        System.out.println(hashCode3);
    }

    /**
     * 通过计算两个文件的hash值比较文件内容是否一致
     *
     * @throws IOException
     */
    @Test
    public void testFileSame() throws IOException {
        HashCode sourceHashCode = Files.asByteSource(new File(SOURCE_FILE)).hash(Hashing.sha256());
        HashCode targetHashCode = Files.asByteSource(new File(SOURCE_FILE1)).hash(Hashing.sha256());

        assertThat(sourceHashCode.toString(),equalTo(targetHashCode.toString()));
    }


    /**
     * 创建文件并追加的模式写入内容到文件
     *
     * @throws IOException
     */
    @Test
    public void testFileWrite() throws IOException {
        final String testFile = "E:\\01.study\\03.google guava\\wamgwenjun\\other\\googleguava\\src\\test\\resources\\io\\source234.txt";
        File sourceFile = new File(testFile);

        //应用程序退出时，删除文件
//        sourceFile.deleteOnExit();

        String content1 = "content 1";

        //以追加的方式以UTF-8编码写入content1到文件【如果文件不存在则自动创建】
        Files.asCharSink(sourceFile,Charsets.UTF_8, FileWriteMode.APPEND).write(content1);//追加写：FileWriteMode.APPEND
//        Files.asCharSink(sourceFile,Charsets.UTF_8).write(content1);//不追加写，覆盖原文件
        //asCharSink是写文件
        //asCharSource是读文件
    }

    /**
     * 创建空文件
     */
    @Test
    public void testTouchFile() throws IOException {
        File touchFile = new File("D:\\05.workspace\\idea\\study\\googleguava\\src\\test\\resources\\io\\source2345.txt");
        touchFile.deleteOnExit();
        Files.touch(touchFile);
        assertThat(touchFile.exists(),equalTo(true));
    }

    /**
     * 递归文件夹中文件
     */
    @Test
    public void testTreeFiles(){
        File root = new File("D:\\05.workspace\\idea\\study\\googleguava\\src\\main");
        FluentIterable<File> files = Files.fileTreeTraverser().preOrderTraversal(root);//preOrderTraversal是正序，postOrderTraversal是倒序、breadthFirstTraversal是按目录宽度从小到大排序
        files.stream().forEach(System.out::println);
    }

    /**
     * 只获取目标文件夹中子
     */
    @Test
    public void testTreeFilesChild(){
        File root = new File("D:\\05.workspace\\idea\\study\\googleguava\\src\\main");
        Iterable<File> files = Files.fileTreeTraverser().children(root);
        files.forEach(System.out::println);
    }

    /**
     * 下面代码是每次单测后，清理单测过程中产生的文件，以保证单测前后数据没有发生变化
     */
    @After
    public void tearDown(){
        File targetFile = new File(TARGET_FILE);
        if (targetFile.exists()){
            targetFile.delete();
        }
    }

}
