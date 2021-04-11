CREATE TABLE shop
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '店铺id',
    name          VARCHAR(100) COMMENT '店铺名',
    description   VARCHAR(1024) COMMENT '店铺描述',
    img_url       VARCHAR(1024) COMMENT '店铺图片url',
    owner_user_id BIGINT COMMENT '店主用户id',
    status        VARCHAR(16) COMMENT '状态 ok正常 deleted 已经删除',
    created_at    TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '创建时间',
    updated_at    TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO shop (id, name, description, img_url, owner_user_id, status)
VALUES (1, 'shop1', 'desc1', 'url1', 1, 'ok');
INSERT INTO shop (id, name, description, img_url, owner_user_id, status)
VALUES (2, 'shop2', 'desc2', 'url2', 1, 'ok');
