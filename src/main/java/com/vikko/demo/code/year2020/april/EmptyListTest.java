package com.vikko.demo.code.year2020.april;

import java.util.Collections;
import java.util.List;

/**
 * @author vikko
 * @date 2020/4/29 16:47
 */
public class EmptyListTest {
    public static void main(String[] args) {
        test();
    }
    public static void test(){
        List<Object> objects = Collections.emptyList();
        List<Object> list = Collections.singletonList(null);
        List<Object> list2 = null;
        list2.forEach(data-> System.out.println(data.toString()));
    }
}
