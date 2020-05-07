package cn.boyce.service.impl;

import cn.boyce.entity.Response;
import cn.boyce.entity.Student;
import cn.boyce.mapper.StudentMapper;
import cn.boyce.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Yuan Baiyu
 * @Date: 2019/11/23
 **/
@Service(value = "mp")
@Slf4j
public class StudentServiceMpImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Value("${REDIS_KEY}")
    private String REDIS_KEY;

    @Value("${REDIS_TIME_OUT}")
    private Integer REDIS_TIME_OUT;

    @Override
    public Response getStudentInfo(Integer sno) {
        List<Student> query = new ArrayList<>();
        if (null == sno) {
            return Response.success(studentMapper.selectList(null));
        }
        query.add(Student.builder().sno(sno).build());
        Student student = studentMapper.selectOne(new QueryWrapper<Student>().lambda().eq(Student::getSno, sno));
        return Response.success(student);
    }

    @Override
    public Response addStudent(Student student) {
        student.setIsDeleted(0);
        log.info("插入数据库！");
        //studentDao.saveAndFlush(student);

        log.info("写入缓存！");
        redisTemplate.opsForHash().put(REDIS_KEY, student.getSno().toString(), student);
        redisTemplate.expire(REDIS_KEY, REDIS_TIME_OUT, TimeUnit.MINUTES);
        log.info("过期时间：{}", redisTemplate.getExpire(REDIS_KEY));
        return Response.successWithoutData("信息添加成功！");
    }

    @Override
    public Response deleteStudent(Integer sno) {
        log.info("数据库删除数据！");
        int row = 1; //studentDao.updateStudentById(sno);
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
//        studentDao.saveAndFlush(student);

        log.info("缓存更新！");
        redisTemplate.opsForHash().put(REDIS_KEY, student.getSno().toString(), student);
        redisTemplate.expire(REDIS_KEY, REDIS_TIME_OUT, TimeUnit.MINUTES);
        log.info("过期时间：{}", redisTemplate.getExpire(REDIS_KEY));
        return Response.successWithoutData("信息更新成功！");
    }
}
