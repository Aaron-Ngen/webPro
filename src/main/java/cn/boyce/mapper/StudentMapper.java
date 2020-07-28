package cn.boyce.mapper;

import cn.boyce.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Yuan Baiyu
 * @Date: 2020/5/6 18:39
 **/
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select * from student where sno > #{sno}")
    List<Student> getStudentList(@Param("sno") Integer sno);

    @Select("select * from student where name like concat('%',#{0},'%')")
    List<Student> getStudentByName(String name);

    @Select("select * from student where sno = #{sno}")
    List<Student> getPageStudent(@Param("sno") Integer sno);
}
