package cn.boyce.controller;

import cn.boyce.entity.Response;
import cn.boyce.entity.Student;
import cn.boyce.service.strategy.FactoryForStrategy;
import cn.boyce.util.annotation.IdemAnnotation;
import cn.boyce.util.annotation.ToolAnnotation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class StudentController {

    @Autowired
    FactoryForStrategy factoryForStrategy;

    @GetMapping("/get")
    @IdemAnnotation
    @ToolAnnotation
    public Response getStudentInfo(@RequestParam Integer sno,
                                   @RequestParam(required = false, defaultValue = "jpa") String impl) {
        return factoryForStrategy.getStrategy(impl).getStudentInfo(sno);
    }

    @PostMapping("/add")
    @IdemAnnotation
    public Response addStudent(@RequestBody Student student,
                               @RequestParam(required = false, defaultValue = "jpa") String impl) {
        return factoryForStrategy.getStrategy(impl).addStudent(student);
    }

    @DeleteMapping("/delete")
    @IdemAnnotation
    public Response deleteStudent(@RequestParam Integer sno,
                                  @RequestParam(required = false, defaultValue = "jpa") String impl) {
        return factoryForStrategy.getStrategy(impl).deleteStudent(sno);
    }

    @PutMapping("/update")
    @IdemAnnotation
    public Response updateStudent(@RequestBody Student student,
                                  @RequestParam(required = false, defaultValue = "jpa") String impl) {
        return factoryForStrategy.getStrategy(impl).updateStudent(student);
    }

}
