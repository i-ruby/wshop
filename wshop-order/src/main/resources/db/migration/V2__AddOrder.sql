use `order`;

INSERT INTO order_table (id, user_id, shop_id, total_price, address, express_company, express_id, status)
VALUES (1, 1, 1, 1400, '火星', '顺丰', '运单1234567', 'delivered');

INSERT INTO order_goods(goods_id, order_id, number)
VALUES (1, 1, 5),
       (2, 1, 9);


INSERT INTO order_table (id, user_id, shop_id, total_price, address, express_company, express_id, status)
VALUES (2, 1, 1, 700, '火星', null, null, 'pending');

INSERT INTO order_goods(goods_id, order_id, number)
VALUES (1, 2, 3),
       (2, 2, 4);