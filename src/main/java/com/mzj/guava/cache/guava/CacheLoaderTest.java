package com.mzj.guava.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * +
 * 基本用法
 * <p>
 * 查询到Employee放入cache，下次从cache直接拿
 */
public class CacheLoaderTest {

    /**
     * 方便进行断言的变量：
     *
     * true：从数据库获取数据
     * false：从缓存获取数据
     */
    private boolean isTrue = false;

    @Test
    public void testBasic() {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().maximumSize(10)//缓存数量
                .expireAfterAccess(2, TimeUnit.SECONDS)//缓存元素过期时间（30s过期）
                .build(new CacheLoader<String, Employee>() {//告诉cache，数据从哪里获取
                    @Override
                    public Employee load(String key) throws Exception {
                        return findEmployeeByName(key);
                    }
                });

        try {
            Employee employee = cache.get("mazhongjia");
            assertThat(employee, notNullValue());
            assertLoadFromDBThenReset();//走DB
            System.out.println(employee);
            employee = cache.get("mazhongjia");
            assertLoadFromCache();//走cache
            System.out.println(employee);

            TimeUnit.SECONDS.sleep(3);//休眠3秒，让cache中元素过期
            employee = cache.get("mazhongjia");
            assertLoadFromDBThenReset();//走DB
            System.out.println(employee);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEvictionByWeigh() {

        /**
         * 定义一个：秤
         */
        Weigher<String, Employee> weigher = ((key, employee) -> employee.getName().length() + employee.getEmpID().length() + employee.getDept().length());

        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().maximumWeight(45)//缓存重量（所有缓存元素的最大重量是：45）
                .concurrencyLevel(1)// 设置并发级别为1，并发级别是指可以同时写缓存的线程数（划分多个segment）
                .weigher(weigher)
                .build(new CacheLoader<String, Employee>() {
                    @Override
                    public Employee load(String key) throws Exception {
                        return findEmployeeByName(key);
                    }
                });

        cache.getUnchecked("gavin");
        assertLoadFromDBThenReset();//走DB
        cache.getUnchecked("kevin");
        assertLoadFromDBThenReset();//走DB
        cache.getUnchecked("allen");
        assertLoadFromDBThenReset();//走DB

        assertThat(cache.size(),equalTo(3L));//断言缓存元素为3个

        assertThat(cache.getIfPresent("gavin"),notNullValue());//getIfPresent：如果存在则返回，如果不存在也不会去DB中拿

        cache.getUnchecked("mamam");
        assertThat(cache.getIfPresent("kevin"),nullValue());//getIfPresent：如果存在则返回，如果不存在也不会去DB中拿；这里已经访问过gavin了，所以目前kevin是最老的未被使用的
        assertThat(cache.size(),equalTo(3L));//断言缓存元素为3个

    }

    /**
     * 断言去DB获取数据
     */
    private void assertLoadFromDBThenReset() {
        assertThat(true, equalTo(this.isTrue));
        this.isTrue = false;
    }

    /**
     * 断言去cache获取数据
     */
    private void assertLoadFromCache() {
        assertThat(false, equalTo(this.isTrue));
    }

    /**
     * 去数据库中通过名称查询Employee
     *
     * @param name
     * @return
     */
    private Employee findEmployeeByName(final String name) {
        this.isTrue = true;
        return new Employee(name, name, name);
    }
}
