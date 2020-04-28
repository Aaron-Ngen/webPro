package cn.boyce.controller;

import cn.boyce.entity.Response;
import cn.boyce.entity.Student;
import cn.boyce.service.StudentService;
import cn.boyce.util.IdempotentApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yuan Baiyu
 * @Date: 2019/11/22
 **/
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/get")
    @IdempotentApi
    public Response getStudentInfo(@RequestParam Integer sno, @RequestParam String message) {
        System.out.println(message);
        return studentService.getStudentInfo(sno);
    }

    @PostMapping("/add")
    @IdempotentApi
    public Response addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @DeleteMapping("/delete")
    @IdempotentApi
    public Response deleteStudent(@RequestParam Integer sno) {
        return studentService.deleteStudent(sno);
    }

    @PutMapping("/update")
    @IdempotentApi
    public Response updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

}
