package cn.boyce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: Yuan Baiyu
 * @Date: 11:51 2019/11/23
 **/
@Configuration
@PropertySource("classpath:conf.yml")
public class SettingConfig {
}
