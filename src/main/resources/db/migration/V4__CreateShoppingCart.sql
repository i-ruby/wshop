CREATE TABLE shopping_cart
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '购物车id',
    user_id    BIGINT COMMENT '用户id',
    goods_id   BIGINT COMMENT '商品id',
    shop_id    BIGINT COMMENT '店铺id',
    number     INT COMMENT '商品数量',
    status     VARCHAR(16) COMMENT '状态 ok正常 deleted 已经删除',
    created_at TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '创建时间',
    updated_at TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO shopping_cart(user_id, goods_id, shop_id, number, status)
VALUES (1, 1, 1, 100, 'ok');
INSERT INTO shopping_cart(user_id, goods_id, shop_id, number, status)
VALUES (1, 4, 2, 200, 'ok');
INSERT INTO shopping_cart(user_id, goods_id, shop_id, number, status)
VALUES (1, 5, 2, 300, 'ok');
