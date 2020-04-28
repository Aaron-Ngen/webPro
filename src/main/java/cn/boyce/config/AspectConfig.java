package cn.boyce.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author: Yuan Baiyu
 * @Date: 18:37 2019/11/25
 **/
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"cn.boyce.util"})
public class AspectConfig {

}
