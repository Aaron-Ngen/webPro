package cn.boyce.service.impl;

import cn.boyce.dao.StudentDao;
import cn.boyce.entity.Response;
import cn.boyce.entity.Student;
import cn.boyce.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Value("${REDIS_TIME_OUT}")
    private Integer REDIS_TIME_OUT;

    @Override
    public Response getStudentInfo(Integer sno) {
        List<Student> result = new ArrayList<>();
        if (null == sno) {
            log.info("缓存获取！");
            result = redisTemplate.opsForHash().entries(REDIS_KEY).values().stream()
                    .map(o -> (Student) o).collect(Collectors.toList());
            result.sort(Comparator.comparing(Student::getSno));
            if (result.size() == 0) {
                log.info("缓存为空，数据库查询！");
                result = studentDao.findAll();
                log.info("缓存写入！");
                redisTemplate.opsForHash().putAll(REDIS_KEY, result.stream()
                        .collect(Collectors.toMap(x -> x.getSno().toString(), x -> x)));
                redisTemplate.expire(REDIS_KEY, REDIS_TIME_OUT, TimeUnit.MINUTES);
                log.info("过期时间：{}", redisTemplate.getExpire(REDIS_KEY));
            }
            return Response.success(result);
        }

        Student student = (Student) redisTemplate.opsForHash().get(REDIS_KEY, sno.toString());
        if (null != student) {
            log.info("缓存获取！");
            result.add(student);
        } else {
            log.info("数据库查询！");
            Optional<Student> stu = studentDao.findById(sno);
            if (stu.isPresent()) {
                result.add(stu.get());

                log.info("缓存写入！");
                redisTemplate.opsForHash().put(REDIS_KEY, sno.toString(), stu.get());
                redisTemplate.expire(REDIS_KEY, REDIS_TIME_OUT, TimeUnit.MINUTES);
                log.info("过期时间：{}", redisTemplate.getExpire(REDIS_KEY));
            }
        }
        return Response.success(result);
    }

    @Override
    public Response addStudent(Student student) {
        student.setIsDeleted(0);
        log.info("插入数据库！");
        studentDao.saveAndFlush(student);

        log.info("写入缓存！");
        redisTemplate.opsForHash().put(REDIS_KEY, student.getSno().toString(), student);
        redisTemplate.expire(REDIS_KEY, REDIS_TIME_OUT, TimeUnit.MINUTES);
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
        redisTemplate.opsForHash().put(REDIS_KEY, student.getSno().toString(), student);
        redisTemplate.expire(REDIS_KEY, REDIS_TIME_OUT, TimeUnit.MINUTES);
        log.info("过期时间：{}", redisTemplate.getExpire(REDIS_KEY));
        return Response.successWithoutData("信息更新成功！");
    }
}
