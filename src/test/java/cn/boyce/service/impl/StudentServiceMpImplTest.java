package cn.boyce.service.impl;


import cn.boyce.entity.Student;
import cn.boyce.mapper.StudentMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: 2020/4/27 19:34
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceMpImplTest {

    @Resource
    private StudentMapper studentMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Student> studentList = studentMapper.selectList(null);
        Assert.assertEquals(5, studentList.size());
        studentList.forEach(System.out::println);
    }

}