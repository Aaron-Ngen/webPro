package cn.boyce.service;

import cn.boyce.entity.Response;
import cn.boyce.entity.Student;

/**
 * @Author: Yuan Baiyu
 * @Date: 2019/11/23
 **/
public interface StudentService {

    Response getStudentInfo(Integer sno);

    Response addStudent(Student student);

    Response deleteStudent(Integer sno);

    Response updateStudent(Student student);
}
