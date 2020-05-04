package cn.boyce.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

/**
 * @Author: Yuan Baiyu
 * @Date: 2019/11/22
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = -4813361542496370884L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sno;

    private int age;

    private String name;

    private int score;

    private String sex;

    private int height;

    private int weight;

    private int isDeleted;
}
