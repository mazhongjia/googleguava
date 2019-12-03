package com.mzj.guava.utilities.precondition;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @Auther: mazhongjia
 * @Date: 2019/12/2 12:39
 * @Version: 1.0
 */
public class PreconditionTest {

    @Test
    public void testCheckNotNull(){
        checkNotNull(null);
    }

    @Test
    public void testCheckNotNullWithMessage(){
        try{
            checkNotNullWithMessage(null);
        }catch (Exception e){
            assertThat(e,is(NullPointerException.class));
            assertThat(e.getMessage(),equalTo("The list should not be null"));
        }

    }

    @Test
    public void testCheckNotNullWithFormatMessage(){
        try{
            checkNotNullWithFormatMessage(null);
        }catch (Exception e){
            assertThat(e,is(NullPointerException.class));
            assertThat(e.getMessage(),equalTo("The list should not be 2"));
        }

    }

    @Test
    public void testCheckNotNullWithArgs(){
        String args1 = "A";
        try{
            Preconditions.checkArgument(args1.equals("B"));
        }catch (Exception e){
            assertThat(e,is(IllegalArgumentException.class));
        }
    }

    @Test
    public void testCheckStatus(){
        String state = "A";
        try{
            Preconditions.checkState(state.equals("B"),"The state is illegal.");
        }catch (Exception e){
            assertThat(e,is(IllegalStateException.class));
        }
    }

    @Test
    public void testCheckIndex(){
        try{
            List<String> list = ImmutableList.of();
            Preconditions.checkElementIndex(10,list.size());
        }catch (Exception e){
            assertThat(e,is(IndexOutOfBoundsException.class));
        }
    }

    @Test(expected = NullPointerException.class)//通过注解进行断言结果处理
    public void testByJava8Objects(){
        Objects.requireNonNull(null);//使用java8中Object进行断言
    }

    @Test(expected = AssertionError.class)
    public void testAssert(){

        List<String> list = null;
        assert list != null;
    }

    private void checkNotNull(final List<String> list){
        Preconditions.checkNotNull(list);
    }

    private void checkNotNullWithMessage(final List<String> list){
        Preconditions.checkNotNull(list,"The list should not be null");
    }

    private void checkNotNullWithFormatMessage(final List<String> list){
        Preconditions.checkNotNull(list,"The list should not be %s",2);
    }


}
