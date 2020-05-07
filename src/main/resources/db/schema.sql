create table student
(
    sno         int auto_increment comment '学号'
        primary key,
    name        varchar(10)       null comment '姓名',
    age         int               null,
    sex         varchar(2)        null,
    score       int               null,
    height      int               null,
    weight      int               null,
    is_deleted  tinyint default 0 null comment '是否删除[0-否；1-是]',
    create_time datetime          null,
    update_time datetime          null
)
    comment '学生表';

create index student_sno_index
    on student (sno);