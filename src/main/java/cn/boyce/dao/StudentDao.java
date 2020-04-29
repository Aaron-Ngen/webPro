package cn.boyce.dao;

import cn.boyce.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Yuan Baiyu
 * @Date: 2019/11/23
 **/
@Repository
public interface StudentDao extends JpaRepository<Student, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update student set is_deleted = 1 where sno = ?1", nativeQuery = true)
    int updateStudentById(@Param("sno") Integer sno);

    @Override
    @Query(value = "select * from student where is_deleted = 0 order by sno", nativeQuery = true)
    List<Student> findAll();

    @Override
    @Query(value = "select * from student where is_deleted = 0 and sno = ?1", nativeQuery = true)
    Optional<Student> findById(@Param("sno") Integer sno);

}

