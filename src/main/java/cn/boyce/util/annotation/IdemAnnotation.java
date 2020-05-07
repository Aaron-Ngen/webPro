package cn.boyce.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 幂等性注解
 * @Author: Yuan Baiyu
 * @Date: 2019/11/22
 **/
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdemAnnotation {

}
