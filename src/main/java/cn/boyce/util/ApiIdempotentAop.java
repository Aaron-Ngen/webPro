package cn.boyce.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: Yuan Baiyu
 * @Date: 22:46 2019/11/23
 **/
@Aspect
@Slf4j
@Component
public class ApiIdempotentAop {

    // @Around(value = "@annotation(cn.boyce.util.IdempotentApi)")
    // public Object invoke(ProceedingJoinPoint joinPoint) {
    //     Signature signature = joinPoint.getSignature();
    //     log.info("name: {}", signature.getName());
    //     log.info("declaringTypeNmae: {}", signature.getDeclaringTypeName());
    //     log.info("modifiers: {}", signature.getModifiers());
    //
    //     log.info("this: {} | transThis: {}", joinPoint.getThis(), (StudentServiceImpl) joinPoint.getThis());
    //     log.info("kind: {}", joinPoint.getKind());
    //     log.info("args: {}", joinPoint.getArgs());
    //     log.info("target: {} | transTarget: {}", joinPoint.getTarget(), (StudentServiceImpl) joinPoint.getTarget());
    //     return null;
    // }

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    private static long startTime;

    @Pointcut("@annotation(cn.boyce.util.IdempotentApi)")
    public void idempotentAop() {

    }

    @Before("idempotentAop()")
    public void before(JoinPoint joinPoint) {
        log.info("===========================================================");
        startTime = System.currentTimeMillis();

        log.info("KeySerializer :{}", redisTemplate.getKeySerializer());
        log.info("ValueSerializer: {}", redisTemplate.getValueSerializer().toString());
        log.info("ConnectionFactory :{}", redisTemplate.getConnectionFactory());
        log.info("HashKeySerializer: {}", redisTemplate.getHashKeySerializer());
        log.info("ValueSerializer: {}", redisTemplate.getValueSerializer());

//        String requestString = "void";
//
//        Signature signature = joinPoint.getSignature();
//        Object[] params = joinPoint.getArgs();
//        log.info("name: {}", signature.getName());
//        log.info("declaringTypeName: {}", signature.getDeclaringTypeName());
//        log.info("modifiers: {}", signature.getModifiers());
//
//        // toShotString, 有参数形如 Hello.hello(..); 无参数形如: Hello.hello()
//        if (signature.toShortString().contains("..")) {
//            requestString = Arrays.toString(params);
//        }
//        log.info("{} 的请求参数为:{}", signature.toShortString(), requestString);
//
//        log.info("this: {}", joinPoint.getThis());
//        log.info("kind: {}", joinPoint.getKind());
//        log.info("args: {}", joinPoint.getArgs());
//        log.info("target: {}", joinPoint.getTarget());
//        System.out.println("===========================================================");
    }

    @After("idempotentAop()")
    public void after() {
        log.info("The calling time of this interface： 【{}ms】", (System.currentTimeMillis() - startTime) );
    }
}
