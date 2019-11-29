package com.mzj.guava.utilites;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.Assert.fail;

/**
 * @link JoinerMapTest
 *
 */
public class JoinerTest {

    private final List<String> stringList = Arrays.asList("google","guava","java","scala");
    private final List<String> stringListWithNullValue = Arrays.asList("google","guava","java","scala","kafka",null);

    @Test
    public void testJoinOnJoin(){
        String result = Joiner.on("#").join(stringList);
        System.out.println(result);
    }

    @Test
    public void testJoinOnJoinWithNotNull(){
        String result = Joiner.on("#").skipNulls().join(stringListWithNullValue);
        System.out.println(result);
    }

    @Test
    public void testJoinOnJoinWithValue_UseDefaultValue(){
        String result = Joiner.on("#").useForNull("DEFAULT").join(stringListWithNullValue);
        System.out.println(result);
    }
    @Test
    public void testJoin_on_Append_To_StringBuilder(){
        final StringBuilder builder = new StringBuilder("123");
        StringBuilder resultBuilder = Joiner.on("#").useForNull("DEFAULT").appendTo(builder,stringListWithNullValue);
        System.out.println(resultBuilder.toString());
    }

    @Test
    public void testJoin_on_Append_To_Writer(){

        String targetFileName = "F:\\03.study\\汪文君Google Guava实战视频\\guava.txt";
        try(FileWriter writer = new FileWriter(new File(targetFileName))){
            Joiner.on("#").useForNull("DEFAULT").appendTo(writer,stringListWithNullValue);
        }catch(IOException e){
            fail("写入Appendable失败，这个Appendable实现是FileWriter");
        }
    }

    @Test
    public void testJoiningByStream(){
        String result = stringListWithNullValue.stream().filter(item -> item != null && !item.isEmpty()).collect(Collectors.joining("#"));
        System.out.println(result);
    }

    @Test
    public void testJoiningByStreamWithDefaultValue(){
        String result = stringListWithNullValue.stream().map(item -> item == null || item.isEmpty() ? "DEFAULT" : item).collect(Collectors.joining("#"));
        System.out.println(result);
    }


}
