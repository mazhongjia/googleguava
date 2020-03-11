package com.mzj.guava.collections;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class TableExampleTest {

    @Test
    public void testHashBasedTable(){
        Table<String,String,String> table = HashBasedTable.create();
        //1、put方法参数：row行key、column列key、值
        table.put("Language","Java","1.8");
        table.put("Language","Python","3.7");
        table.put("DataBase","oracle","12C");
        table.put("DataBase","mysql","8.0");
        table.put("MyLanguage","Java","144");
        System.out.println(table);

        //2、拿到某一行
        Map<String,String> language = table.row("Language");
        System.out.println(language);

        //3、table结构类似于：Map<rowKey,Map<columnKey,value>>

        //4、可以直接拿到某列
        Map<String, String> column = table.column("Java");
        System.out.println(column);

        //5、cellSet
        Set<Table.Cell<String, String, String>> cells = table.cellSet();
        System.out.println(cells);

    }
}
