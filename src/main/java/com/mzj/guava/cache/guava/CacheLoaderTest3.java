package com.mzj.guava.cache.guava;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * 测试cache中空元素的处理
 */
public class CacheLoaderTest3 {

    /**
     * 测试没有经过任何处理时cacheLoader的load方法如果返回null值，会抛出异常
     */
    @Test
    public void testLoadNullValue() {

        /**
         * cacheLoader的load方法如果返回null值，会抛出异常
         */
        CacheLoader<String, Employee> cacheLoader = CacheLoader.from(k -> k.equals("null") ? null : new Employee(k, k, k));

        LoadingCache<String,Employee> cache = CacheBuilder.newBuilder().build(cacheLoader);

        Employee alax = cache.getUnchecked("Alex");

        assertThat(alax,notNullValue());
        assertThat(alax.getName(),equalTo("Alex"));

        try{
            Employee employeeNull = cache.getUnchecked("null");//此行会抛出异常，由于cacheLoader的load方法由于参数"null"而返回null
            assertThat(employeeNull,nullValue());
           fail("should not process to here.");
        }catch (Exception e){
            e.printStackTrace();
            assertThat(e instanceof CacheLoader.InvalidCacheLoadException,equalTo(true));
        }
    }

    /**
     * 测试：通过使用guava的Optional包装了缓存中元素，使得cacheLoader的load方法不可能返回null，而解决了上面测试用例中异常
     */
    @Test
    public void testLoadNullValueUseOptional(){
        CacheLoader<String, Optional<Employee>> loader = new CacheLoader<String, Optional<Employee>>() {
            @Override
            public Optional<Employee> load(String key) throws Exception {
                if (key.equals("null"))
                    return Optional.fromNullable(null);
                else
                    return Optional.fromNullable(new Employee(key,key,key));
            }
        };
        LoadingCache<String,Optional<Employee>> cache = CacheBuilder.newBuilder().build(loader);

        Optional<Employee> alax = cache.getUnchecked("Alex");
        assertThat(alax.get(),notNullValue());

        /**
         * 1、此行断言体现了从cache获取到到Optional为Absent的实现（代表空的Optional）但是并未返回null
         */
        assertThat(cache.getUnchecked("null").orNull(),nullValue());//不能调用cache.getUnchecked("null")的get会抛异常（返回Absent的实现）

        /**
         * 2、此行断言体现了更进一步，如果为”null的Optional，则使用默认值
         *
         * 好处是不再需要进行判断：从cache中获取的内容是否为null，没有则返回默认值
         */
        Employee def = cache.getUnchecked("null").or(new Employee("default","default","default"));
        assertThat(def,notNullValue());
    }
}
