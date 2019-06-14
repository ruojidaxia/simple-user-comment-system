package com.fgj.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class BeanUtilsTest {
    @Setter
    @Getter
    public static class A{
        private Integer id;
        private String name;
        private B b;
    }
    @Setter
    public static class ACopy{
        private Integer id;
        private String name;
        private B b;
    }
    public static class B{
        private Integer id;
        private String name;

        public B(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    @Test
    public void test() throws JsonProcessingException {
        A a = new A();
        a.setId(1);
        a.setName("111");
        B b = new B(2,"222");
        a.setB(b);
        ACopy a1 = new ACopy();
        BeanUtils.copyProperties(a,a1);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(a));
        System.out.println(mapper.writeValueAsString(a1));
    }
    @Test
    public void testGetNullProperties(){
        A a = new A();
//        a.setId(1);
//        a.setName("111");
        B b = new B(2,"222");
        a.setB(b);
        BeanWrapper beanWrapper = new BeanWrapperImpl(a);
        String[] properties = Stream.of(beanWrapper.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(name-> Objects.isNull(beanWrapper.getPropertyValue(name)))
                .toArray(String[]::new);
        System.out.println(Arrays.toString(properties));
    }
}
