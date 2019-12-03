package com.mzj.guava.utilities.spliter;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Auther: mazhongjia
 * @Date: 2019/11/29 12:56
 * @Version: 1.0
 */
public class SplitterTest {

    @Test
        public void testSplitOnSplit(){//splitToList：结果转list
        List<String> result = Splitter.on("|").splitToList("hello|world");
        System.out.println(result);
    }

    @Test
    public void testSplit_On_Split_OmitEmpty(){//omitEmptyStrings：去空值
        List<String> result = Splitter.on("|").omitEmptyStrings().splitToList("hello|world|||");
        System.out.println(result);
    }

    @Test
    public void testSplit_On_Split_OmitEmpty_TrimResult(){//trimResults：去空格
        List<String> result = Splitter.on("|").trimResults().omitEmptyStrings().splitToList("hello   |   world|||");
        System.out.println(result);
    }

    @Test
    public void testSplitFixLength(){
        List<String> result = Splitter.fixedLength(4).splitToList("aaaaaaaaaaabbbbcccccdddddddddddd");
        System.out.println(result);
    }

    @Test
    public void testSplitFOnSplitLimit(){
        List<String> result = Splitter.on("#").limit(3).splitToList("aaaaaa#aaaaa#bbbb#cccccd#ddddd#dddddd");
        System.out.println(result);
    }

    @Test
    public void testSplitFOnPatternString(){//传递正则
        List<String> result = Splitter.onPattern("\\|").trimResults().omitEmptyStrings().splitToList("hello          |       world      |||");
        System.out.println(result);
    }

    @Test
    public void testSplitFOnSplitToMap(){//withKeyValueSeparator：结果转map
        Map<String,String> result = Splitter.on(Pattern.compile("\\|")).trimResults().omitEmptyStrings().withKeyValueSeparator("=").split("hello=He          |       world=Wor      |||");
        System.out.println(result);
    }
}
