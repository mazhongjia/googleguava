package com.mzj.guava.utilities.strings;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @Auther: mazhongjia
 * @Date: 2019/12/4 12:30
 * @Version: 1.0
 */
public class StringsTest {

    @Test
    public void testStringsMethod(){
        assertThat(Strings.emptyToNull(""),nullValue());
        assertThat(Strings.nullToEmpty(null),equalTo(""));
        assertThat(Strings.nullToEmpty("hello"),equalTo("hello"));
        assertThat(Strings.commonPrefix("Hello","Hit"),equalTo("H"));//公共的前缀
        assertThat(Strings.commonSuffix("Hello","Echo"),equalTo("o"));//公共的后缀
        assertThat(Strings.repeat("Hello",3),equalTo("HelloHelloHello"));
        assertThat(Strings.isNullOrEmpty(null),equalTo(true));
        assertThat(Strings.isNullOrEmpty(""),equalTo(true));

        assertThat(Strings.padStart("Alex",3,'H'),equalTo("Alex"));//如果字符串长度不够minLength，则用padChar填充前面
        assertThat(Strings.padStart("Alex",5,'H'),equalTo("HAlex"));
        assertThat(Strings.padEnd("Alex",5,'H'),equalTo("AlexH"));//如果字符串长度不够minLength，则用padChar填充后面
    }

    @Test
    public void testCharsets(){
        Charset charset = Charset.forName("UTF-8");
        assertThat(Charsets.UTF_8,equalTo(charset));
    }

    @Test
    public void testCharMatcher(){
        assertThat(CharMatcher.javaDigit().matches('5'),equalTo(true));//字符是否是java数值类型
        assertThat(CharMatcher.javaDigit().matches('x'),equalTo(false));//字符是否是java数值类型

        assertThat(CharMatcher.is('A').countIn("Alex Sharing the Google"),equalTo(1));//统计字符串中字符个数
        assertThat(CharMatcher.breakingWhitespace().collapseFrom("               hello guava        ",'*'),equalTo("*hello*guava*"));//将连续的空格替换成一个指定字符

        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).removeFrom("hello 234 world"),equalTo("helloworld"));//把字符串中数字（CharMatcher.javaDigit()）或者空格（CharMatcher.whitespace()）进行移除
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).retainFrom("hello 234 world"),equalTo(" 234 "));//把字符串中数字（CharMatcher.javaDigit()）或者空格（CharMatcher.whitespace()）进行保留

    }
}
