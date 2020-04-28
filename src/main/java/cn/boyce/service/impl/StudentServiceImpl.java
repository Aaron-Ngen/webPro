package cn.boyce.service.impl;

import cn.boyce.dao.StudentDao;
import cn.boyce.entity.Response;
import cn.boyce.entity.Student;
import cn.boyce.service.StudentService;
import cn.boyce.util.IdempotentApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Yuan Baiyu
 * @Date: 2019/11/23
 **/
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentDao studentDao;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Value("${REDIS_KEY}")
    private String REDIS_KEY;

    @Override
    public Response getStudentInfo(Integer sno) {
        List<Student> list = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        if (null == sno) {
            Object obj = redisTemplate.opsForHash().entries(REDIS_KEY);
            if (null == obj) {
                return Response.success(studentDao.findAll());
            } else {
                return Response.success(obj);
            }
        }
        Student student = (Student) redisTemplate.opsForHash().get(REDIS_KEY, sno.toString());
        if (null != student) {
            log.info("缓存获取！");
            list.add(student);
        } else {
            log.info("数据库查询！");
            Optional<Student> stu = studentDao.findById(sno);
            if (stu.isPresent()) {
                list.add(stu.get());

                log.info("缓存写入！");
                redisTemplate.opsForHash().put(REDIS_KEY, sno.toString(), student);
                redisTemplate.expire(REDIS_KEY, 2 * 60, TimeUnit.SECONDS);
                log.info("过期时间：{}", redisTemplate.getExpire(REDIS_KEY));
            }
        }
        log.info("cost time :{}", System.currentTimeMillis() - startTime);
        return Response.success(list);
    }

    @Override
    public Response addStudent(Student student) {
        student.setIsDeleted(0);
        log.info("插入数据库！");
        studentDao.saveAndFlush(student);

        log.info("写入缓存！");
        redisTemplate.opsForHash().put(REDIS_KEY, String.valueOf(student.getSno()), student);
        redisTemplate.expire(REDIS_KEY, 2 * 60, TimeUnit.SECONDS);
        log.info("过期时间：{}", redisTemplate.getExpire(REDIS_KEY));
        return Response.successWithoutData("信息添加成功！");
    }

    @Override
    public Response deleteStudent(Integer sno) {
        log.info("数据库删除数据！");
        int row = studentDao.updateStudentById(sno);
        if (row != 1) {
            return Response.SERVER_FAIL;
        }

        log.info("缓存删除！");
        redisTemplate.opsForHash().delete(REDIS_KEY, sno.toString());
        return Response.successWithoutData("信息删除成功！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response updateStudent(Student student) {
        log.info("数据库插入数据！");
        studentDao.saveAndFlush(student);

        log.info("缓存更新！");
        redisTemplate.opsForHash().put(REDIS_KEY, student.getSno(), student);
        redisTemplate.expire(REDIS_KEY, 2 * 60, TimeUnit.SECONDS);
        log.info("过期时间：{}", redisTemplate.getExpire(REDIS_KEY));
        return Response.successWithoutData("信息更新成功！");
    }
}
