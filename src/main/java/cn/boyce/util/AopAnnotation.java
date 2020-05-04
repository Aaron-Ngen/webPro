package cn.boyce.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Yuan Baiyu
 * @Date: 2019/11/22
 **/
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AopAnnotation {

}
