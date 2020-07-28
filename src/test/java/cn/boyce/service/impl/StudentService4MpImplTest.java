package cn.boyce.service.impl;


import cn.boyce.entity.Student;
import cn.boyce.mapper.StudentMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class StudentService4MpImplTest {

    @Resource
    private StudentMapper studentMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Student> studentList = studentMapper.selectList(null);
        Assert.assertEquals(5, studentList.size());
        studentList.forEach(System.out::println);
    }

    @Test
    public void testSelect1() {
        List<Student> list = studentMapper.getStudentList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void testSelectByName() {
        List<Student> list = studentMapper.getStudentByName("袁");
        list.forEach(System.out::println);
    }

    @Test
    public void testPageSelect() {
        List<Student> list = studentMapper.getPageStudent(1);
        System.out.println(list);
    }
}