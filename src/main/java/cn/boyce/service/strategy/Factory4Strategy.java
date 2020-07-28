package cn.boyce.service.strategy;

import cn.boyce.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Yuan Baiyu
 * @Date: 2020/5/7 11:25
 **/
@Service
public class Factory4Strategy {

    @Autowired
    Map<String, StudentService> strategy = new ConcurrentHashMap<>(3);

    public StudentService getStrategy(String component) {
        StudentService strategy = this.strategy.get(component);
        if (strategy == null) {
            throw new RuntimeException("no studentService defined");
        }
        return strategy;
    }

}
