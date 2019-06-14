package com.fgj.comment;

import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.regex.Pattern;

public class CommonTests {

    @Test
    public void contextLoads1() {
        Pattern p = Pattern.compile(".*");
        System.out.println(p.matcher("").matches());
    }
    @Test
    public void contextLoads2() {
        Integer s = null;
        System.out.println(s > 0);
    }
    public void go( Integer i){
        System.out.println(i);
    }
    @Test
    public void test3(){
        go(null);
    }
    @Test
    public void test5(){
        Integer i = 166;
        int i2 = 166;
        Integer i3 = 166;
        System.out.println(i.equals(i2));
        System.out.println(i == i2);
        System.out.println(i.equals(i3));
        System.out.println(i == i3);
    }
    @Test
    public void test6(){
        Vector list = new Vector();
        System.out.println(list.size());
        System.out.println(list.capacity());
        Collection c;
        List l;
        Map m;
        HashMap hm;
        Set s;
    }
    @Data
    public class A{
        private int id;
    }
    public class B{
        private int id;
    }
    @Test
    public void test11(){
    }
}
