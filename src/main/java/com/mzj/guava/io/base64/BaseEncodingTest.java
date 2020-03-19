package com.mzj.guava.io.base64;

import com.google.common.io.BaseEncoding;
import org.junit.Test;

public class BaseEncodingTest {

    @Test
    public void testBase64Encode(){
        BaseEncoding baseEncoding = BaseEncoding.base64();
        System.out.println(baseEncoding.encode("hello".getBytes()));
    }

    @Test
    public void testBase64Decode(){
        BaseEncoding baseEncoding = BaseEncoding.base64();
        System.out.println(new String(baseEncoding.decode("aGVsbG8=")));
    }
}
