package com.fgj.comment.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

public class SnakeBeanUtils {

    /**
     * 将source的非空字段赋值给target
     * @param source
     * @param target
     */
    public static void copyNonNullProp(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * 将source的非空字段赋值给target（忽略审计字段）
     * @param source
     * @param target
     */
    public static void copyNonNullPropIgnoreAudit(Object source, Object target) {
        String[] nullPropertyNames = getNullPropertyNames(source);
        String[] ignorePropertyNames = ArrayUtils.addAll(nullPropertyNames,"createdDate","createdBy","modifiedDate","modifiedBy");
        BeanUtils.copyProperties(source, target, ignorePropertyNames);
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public static String[] getNonNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) != null)
                .toArray(String[]::new);
    }

    public static <T> T fromSource(Object source,Class<T> clazz){
        return fromSource(source,clazz,"");
    }

    public static <T> T fromSourceIgnoreAudit(Object source,Class<T> clazz){
        return fromSource(source,clazz,"createdDate","createdBy","modifiedDate","modifiedBy");
    }

    public static <T> T fromSourceIgnoreAudit(Object source,Class<T> clazz,String... ignoreProperties){
        String[] ignoreProps = ArrayUtils.addAll(ignoreProperties,"createdDate","createdBy","modifiedDate","modifiedBy");
        return fromSource(source,clazz,ignoreProps);
    }

    public static <T> T fromSource(Object source,Class<T> clazz,String... ignoreProperties){
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(source,t,ignoreProperties);
        return t;
    }
}
