package cn.boyce.controller;

import cn.boyce.entity.Response;
import cn.boyce.entity.Student;
import cn.boyce.service.strategy.Factory4Strategy;
import cn.boyce.util.annotation.IdemAnnotation;
import cn.boyce.util.annotation.ToolAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@Slf4j
public class StudentController {

    @Autowired
    Factory4Strategy factory4Strategy;

    @Value("${strategy-4impl}")
    private String SERVICE4IMPL;

    @GetMapping()
    @ToolAnnotation
    public Response getStudentInfo(@RequestParam Integer sno) {
        return factory4Strategy.getStrategy(SERVICE4IMPL).getStudentInfo(sno);
    }

    @PostMapping()
    @IdemAnnotation
    public Response addStudent(@RequestBody Student student) {
        return factory4Strategy.getStrategy(SERVICE4IMPL).addStudent(student);
    }

    @DeleteMapping()
    @IdemAnnotation
    public Response deleteStudent(@RequestParam Integer sno) {
        return factory4Strategy.getStrategy(SERVICE4IMPL).deleteStudent(sno);
    }

    @PutMapping()
    @IdemAnnotation
    public Response updateStudent(@RequestBody Student student) {
        return factory4Strategy.getStrategy(SERVICE4IMPL).updateStudent(student);
    }

}
