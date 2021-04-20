CREATE TABLE order_table
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    user_id         BIGINT COMMENT '用户名',
    shop_id         BIGINT COMMENT '用户名',
    total_price     BIGINT COMMENT '价格，单位分',
    address         VARCHAR(1024) COMMENT '地址',
    express_company VARCHAR(16) COMMENT '物流商',
    express_id      VARCHAR(128) COMMENT '物流id',
    status          VARCHAR(16) COMMENT 'PENDING 待付款 PAID 已付款 DELIVERED 物流中 RECEIVED 已收货',
    created_at TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '创建时间',
    updated_at TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE order_goods
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    goods_id BIGINT COMMENT '商品id',
    order_id BIGINT COMMENT '订单id',
    number   BIGINT COMMENT '价格 单位分'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

