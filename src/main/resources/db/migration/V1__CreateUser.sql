CREATE TABLE user
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户id',
    name       VARCHAR(100) COMMENT '用户名',
    tel        VARCHAR(20) UNIQUE COMMENT '用户电话',
    avatar_url VARCHAR(1024) COMMENT '用户头像url',
    address    VARCHAR(1024) COMMENT '用户地址',
    created_at TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '创建时间',
    updated_at TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO user(id, name, tel, avatar_url, address)
VALUES (1, 'user1', '13800000000', 'http://url', '火星')