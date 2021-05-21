package work.iruby.wshop.main.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import work.iruby.wshop.common.dao.DataMessage;
import work.iruby.wshop.common.dao.OrderData;
import work.iruby.wshop.common.dao.OrderExpressAndStatus;
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.main.service.IOrderService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author iruby
 * @since 2021-04-19
 */
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * @api {post} /order 下订单
     * @apiName CreateOrder
     * @apiGroup 订单
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParamExample {json} Request-Example:
     *            {
     *              "goods": [
     *                {
     *                    "id": 12345,
     *                    "number": 10,
     *                },
     *                {
     *                    ...
     *                }
     *            }
     *
     * @apiSuccess {Order} data 刚刚创建完成的订单
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *       "data": {
     *           "id": 12345,
     *           "expressCompany": null,
     *           "expressId": null,
     *           "status": "pending",
     *           "address": "XXX",
     *           "shop": {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *              "ownerUserId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *            },
     *            "goods": [
     *              {
     *                  "id": 12345,
     *                  "name": "肥皂",
     *                  "description": "纯天然无污染肥皂",
     *                  "details": "这是一块好肥皂",
     *                  "imgUrl": "https://img.url",
     *                  "address": "XXX",
     *                  "price": 500,
     *                  "number": 10,
     *                  "createdAt": "2020-03-22T13:22:03Z",
     *                  "updatedAt": "2020-03-22T13:22:03Z"
     *              },
     *              {
     *                    ...
     *              }
     *           ]
     *         }
     *     }
     *
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 404 Not Found 若商品未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "商品已经售完"
     *     }
     */
    /**
     * 新增订单
     *
     * @param orderData 订单信息
     * @return 订单信息
     */
    @PostMapping("/order")
    public Object addOrder(@RequestBody OrderData orderData, HttpServletResponse response) {
        if (orderData.getGoods() == null || orderData.getGoods().size() == 0) {
            return DataMessage.of(null);
        }
        Long totalPrice = orderService.deductStockAndCalTotalPrice(orderData);
        orderData.setTotalPrice(totalPrice);
        response.setStatus(HttpStatus.CREATED.value());
        return orderService.addOrder(orderData);
    }

    /**
     * @api {GET} /order/:id 根据id查看订单
     * @apiName GetOrderById
     * @apiGroup 订单
     * @apiHeader {String} Accept application/json
     * @apiSuccess {Order} data 刚刚删除的订单
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "data": {
     * "id": 12345,
     * "expressCompany": null,
     * "expressId": null,
     * "status": "pending",
     * "address": "XXX",
     * "shop": {
     * "id": 12345,
     * "name": "我的店铺",
     * "description": "我的苹果专卖店",
     * "imgUrl": "https://img.url",
     * "ownerUserId": 12345,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * },
     * "goods": [
     * {
     * "id": 12345,
     * "name": "肥皂",
     * "description": "纯天然无污染肥皂",
     * "details": "这是一块好肥皂",
     * "imgUrl": "https://img.url",
     * "address": "XXX",
     * "price": 500,
     * "number": 10,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * },
     * {
     * ...
     * }
     * ]
     * }
     * }
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户查看非自己订单
     * @apiError 404 Not Found 若订单未找到
     * @apiErrorExample Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     * "message": "Not Found"
     * }
     */
    /**
     * 根据id查看订单
     *
     * @param id 订单id
     * @return 订单信息
     */
    @GetMapping("/order/{id}")
    public Object getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderByOrderId(id);
    }

    /**
     * @api {DELETE} /order/:id 删除订单
     * @apiName DeleteOrder
     * @apiGroup 订单
     * @apiHeader {String} Accept application/json
     * @apiSuccess {Order} data 刚刚删除的订单
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 204 No Content
     * {
     * "data": {
     * "id": 12345,
     * "expressCompany": null,
     * "expressId": null,
     * "status": "pending",
     * "address": "XXX",
     * "shop": {
     * "id": 12345,
     * "name": "我的店铺",
     * "description": "我的苹果专卖店",
     * "imgUrl": "https://img.url",
     * "ownerUserId": 12345,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * },
     * "goods": [
     * {
     * "id": 12345,
     * "name": "肥皂",
     * "description": "纯天然无污染肥皂",
     * "details": "这是一块好肥皂",
     * "imgUrl": "https://img.url",
     * "address": "XXX",
     * "price": 500,
     * "number": 10,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * },
     * {
     * ...
     * }
     * ]
     * }
     * }
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户删除非自己订单
     * @apiError 404 Not Found 若订单未找到
     * @apiErrorExample Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     * "message": "Not Found"
     * }
     */
    /**
     * /**
     * 根据id删除订单
     *
     * @param orderId  订单id
     * @param response response
     * @return 订单信息
     */
    @DeleteMapping("/order/{id}")
    public Object deleteOrderByOrderId(@PathVariable(value = "id") Long orderId, HttpServletResponse response) {
        response.setStatus(HttpStatus.NO_CONTENT.value());
        return orderService.deleteOrderByOrderId(orderId);
    }

    /**
     * @api {PATCH} /order/:id 更新订单(只能更新物流信息/签收状态)
     * @apiName UpdateOrder
     * @apiGroup 订单
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {Number} id 订单ID
     * @apiParamExample {json} Request-Example:
     *          {
     *              "id": 12345,
     *              "expressCompany": "圆通",
     *              "expressId": "YTO1234",
     *          }
     *          {
     *              "id": 12345,
     *              "status": "RECEIVED"
     *          }
     *
     *
     * @apiSuccess {Order} data 更新后的订单
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "data": {
     *           "id": 12345,
     *           "expressCompany": null,
     *           "expressId": null,
     *           "status": "pending",
     *           "address": "XXX",
     *           "shop": {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *              "ownerUserId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *            },
     *            "goods": [
     *              {
     *                  "id": 12345,
     *                  "name": "肥皂",
     *                  "description": "纯天然无污染肥皂",
     *                  "details": "这是一块好肥皂",
     *                  "imgUrl": "https://img.url",
     *                  "address": "XXX",
     *                  "price": 500,
     *                  "number": 10,
     *                  "createdAt": "2020-03-22T13:22:03Z",
     *                  "updatedAt": "2020-03-22T13:22:03Z"
     *              },
     *              {
     *                    ...
     *              }
     *           ]
     *         }
     *     }
     *
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户修改非自己店铺的订单
     * @apiError 404 Not Found 若订单未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *       "message": "Not Found"
     *     }
     */
    /**
     * 根据id更新订单(只能更新物流信息/签收状态)
     *
     * @param orderId               订单id
     * @param orderExpressAndStatus 用来修改的订单实体
     * @return 订单信息
     */
    @PatchMapping("/order/{id}")
    public Object updateOrderByOrderId(@PathVariable(value = "id") Long orderId, @RequestBody OrderExpressAndStatus orderExpressAndStatus) {
        return orderService.updateOrderByOrderId(orderId, orderExpressAndStatus);
    }

    /**
     * @api {get} /order 获取当前用户名下的所有订单
     * @apiName GetOrder
     * @apiGroup 订单
     *
     * @apiHeader {String} Accept application/json
     *
     * @apiParam {Number} pageNum 页数，从1开始
     * @apiParam {Number} pageSize 每页显示的数量
     * @apiParam {String=pending/paid/delivered/received} [status] 订单状态：pending 待付款 paid 已付款 delivered 物流中 received 已收货
     *
     * @apiSuccess {Number} pageNum 页数，从1开始
     * @apiSuccess {Number} pageSize 每页显示的数量
     * @apiSuccess {Number} totalPage 共有多少页
     * @apiSuccess {Order} data 订单列表
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "pageNum": 1,
     *       "pageSize": 10,
     *       "totalPage": 5,
     *       "data": [
     *          {
     *           "id": 12345,
     *           "expressCompany": null,
     *           "expressId": null,
     *           "status": "pending",
     *           "totalPrice": 10000,
     *           "address": "XXX",
     *           "shop": {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *              "ownerUserId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *            },
     *            "goods": [
     *              {
     *                  "id": 12345,
     *                  "name": "肥皂",
     *                  "description": "纯天然无污染肥皂",
     *                  "details": "这是一块好肥皂",
     *                  "imgUrl": "https://img.url",
     *                  "address": "XXX",
     *                  "price": 500,
     *                  "number": 10,
     *                  "createdAt": "2020-03-22T13:22:03Z",
     *                  "updatedAt": "2020-03-22T13:22:03Z"
     *              },
     *              {
     *                    ...
     *              }
     *           ]
     *         },
     *         {
     *              ...
     *         }
     *       ]
     *     }
     *
     * @apiError 401 Unauthorized 若用户未登录
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    /**
     * 分页获取当前用户名下的所有订单
     *
     * @param pageNum  页数，从1开始
     * @param pageSize 每页显示的数量
     * @param status   订单状态：pending 待付款 paid 已付款 delivered 物流中 received 已收货
     * @return 订单列表
     */
    @GetMapping("/order")
    public PageMessage<List<OrderData>> getCurrentUserPageOrders(@RequestParam(name = "pageNum") Integer pageNum,
                                                                 @RequestParam(name = "pageSize") Integer pageSize,
                                                                 @RequestParam(name = "status", required = false) String status) {
        return orderService.getCurrentUserPageOrders(pageNum, pageSize, status);
    }

}

