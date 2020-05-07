create table student
(
    sno        int auto_increment comment '学号'
        primary key,
    name       varchar(10)       null comment '姓名',
    age        int               null,
    sex        varchar(2)        null,
    score      int               null,
    height     int               null,
    weight     int               null,
    is_deleted tinyint default 0 null comment '是否删除[0-否；1-是]'
)
    comment '学生表';

create index student_sno_index
    on student (sno);

INSERT INTO demo.student (sno, name, age, sex, score, height, weight, is_deleted)
VALUES
 (1, '袁大山', 23, '男', 120, 160, 45, 0),
 (2, '袁小树', 32, '男', 135, 180, 75, 0),
 (3, 'ceshi', 19, '男', 150, 168, 62, 0),
 (4, 'suibian', 56, '女', 120, 175, 87, 0),
 (153, 'kk', 10, '?', 85, 212, 150, 0);