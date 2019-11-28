package com.mzj.guava.utilites;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static junit.framework.Assert.fail;

public class JoinerMapTest {

    private final Map<String,String> stringMap = ImmutableMap.of("key1","value1","key2","value2","key3","value3");

    @Test
    public void testJoinOnWithMap(){
        String result = Joiner.on("#").withKeyValueSeparator("=").join(stringMap);
        System.out.println(result);
    }

    @Test
    public void testJoin_on_WithMap_To_Appendable(){

        String targetFileName = "F:\\03.study\\汪文君Google Guava实战视频\\guava-map.txt";
        try(FileWriter writer = new FileWriter(new File(targetFileName))){
            Joiner.on("#").withKeyValueSeparator("=").appendTo(writer,stringMap);
        }catch(IOException e){
            fail("写入Appendable失败，这个Appendable实现是FileWriter");
        }
    }

}
