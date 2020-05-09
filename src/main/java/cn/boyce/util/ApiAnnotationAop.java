package cn.boyce.util;

import cn.boyce.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Yuan Baiyu
 * @Date: 22:46 2019/11/23
 **/
@Aspect
@Slf4j
@Component
public class ApiAnnotationAop {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    @Value("${idempotent_key}")
    private String IDEMPOTENT_KEY;

    @Value("${idempotent_time_out}")
    private long IDEMPOTENT_TIME_OUT;

    @Pointcut("@annotation(cn.boyce.util.annotation.ToolAnnotation)")
    public void ToolAnnotation() {

    }

    @Pointcut("@annotation(cn.boyce.util.annotation.IdemAnnotation)")
    public void IdemAnnotation() {

    }

    @Around("ToolAnnotation()")
    public Object toolInterface(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("tool start: {}", new Date());
        threadLocal.set(System.currentTimeMillis());
        Object result = joinPoint.proceed();
        log.info("{}", System.currentTimeMillis() - threadLocal.get());
        log.info("tool end: {}", new Date());
        return result;
    }

    /**
     * 方法缓存，缓存当前线程的变量
     */
    @Around("IdemAnnotation()")
    public Object threadCache(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("idem start: {}", new Date());
        Object result = doCache(joinPoint);
        log.info("idem end: {}", new Date());
        return result;
    }

    /**
     * 缓存操作
     */
    public Object doCache(ProceedingJoinPoint joinPoint) throws Throwable {
        String key = getKey(joinPoint);
        return getValue(key, joinPoint);
    }

    /**
     * 校验
     */
    private Object getValue(String key, ProceedingJoinPoint joinPoint) throws Throwable {
        synchronized (this) {
            if (null != redisTemplate.opsForValue().get(key)) {
                return Response.fail("当前操作已完成！");
            }
            redisTemplate.opsForValue().set(key, "幂等性接口", IDEMPOTENT_TIME_OUT, TimeUnit.SECONDS);
        }
        return joinPoint.proceed();
    }

    /**
     * key结构 : 类名+方法名+参数列表
     */
    private String getKey(ProceedingJoinPoint joinPoint) {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(joinPoint.getTarget().getClass().getTypeName())
                .append(".").append(joinPoint.getSignature().getName())
                .append("(");
        if (joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                keyBuilder.append(joinPoint.getArgs()[i]);
                if (i != (joinPoint.getArgs().length - 1)) {
                    keyBuilder.append(",");
                } else {
                    keyBuilder.append(")");
                }
            }
        } else {
            keyBuilder.append(")");
        }
        return keyBuilder.toString();
    }
}
