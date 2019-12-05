package com.mzj.guava.functional;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

/**
 * @Auther: mazhongjia
 * @Date: 2019/12/5 12:48
 * @Version: 1.0
 */
public class FunctionExample {

    public static void main(String[] args) {
        Function<String,Integer> function = new Function<String,Integer>(){

            @Nullable
            @Override
            public Integer apply(@Nullable String input) {
                Preconditions.checkNotNull(input);
                return input.length();
            }
        };

        System.out.println(function.apply("hello"));
    }
}
