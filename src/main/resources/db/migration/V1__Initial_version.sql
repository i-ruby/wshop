create table wshop_user
(
    user_id         int primary key auto_increment comment '用户id',
    user_name       varchar(100) comment '用户名',
    user_tel        varchar(30) unique comment '电话号码',
    user_avatar_url varchar(1024) comment '头像url',
    created_at      timestamp comment '创建时间',
    updated_at      timestamp comment '修改时间'
);