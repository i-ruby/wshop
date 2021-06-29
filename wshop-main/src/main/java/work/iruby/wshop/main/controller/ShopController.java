package work.iruby.wshop.main.controller;


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
import work.iruby.wshop.common.dao.PageMessage;
import work.iruby.wshop.common.entity.Shop;
import work.iruby.wshop.main.service.IShopService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author iruby
 * @since 2021-04-12
 */
@RestController
@RequestMapping("/api/v1")
public class ShopController {

    private final IShopService shopService;

    public ShopController(IShopService shopService) {
        this.shopService = shopService;
    }

    /**
     * @api {post} /shop 创建店铺
     * @apiName CreateShop
     * @apiGroup 店铺
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParamExample {json} Request-Example:
     *          {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *          }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *       "data": {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *              "ownerUserId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *       }
     *     }
     *
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 401 Unauthorized 若用户未登录
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    /**
     * 创建店铺
     *
     * @param shop     店铺信息
     * @param response response
     * @return 店铺信息
     */
    @PostMapping("/shop")
    public DataMessage<Shop> creatShop(@RequestBody Shop shop, HttpServletResponse response) {
        response.setStatus(HttpStatus.CREATED.value());
        return shopService.creatShop(shop);
    }

    /**
     * @api {DELETE} /shop/:id 删除店铺
     * @apiName DeleteShop
     * @apiGroup 店铺
     *
     * @apiHeader {String} Accept application/json
     *
     * @apiParam {Number} id 店铺ID
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 204 No Content
     *     {
     *       "data": {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *              "ownerUserId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *       }
     *     }
     *
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 404 Not Found 若店铺未找到
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户尝试删除非自己管理店铺
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    /**
     * 根据店铺id删除店铺
     *
     * @param shopId   店铺id
     * @param response response
     * @return 店铺信息
     */
    @DeleteMapping("/shop/{id}")
    public DataMessage<Shop> deleteShopByShopId(@PathVariable(value = "id") Long shopId, HttpServletResponse response) {
        response.setStatus(HttpStatus.NO_CONTENT.value());
        return shopService.deleteShopByShopId(shopId);
    }

    /**
     * @api {PATCH} /shop/:id 修改店铺
     * @apiName UpdateShop
     * @apiGroup 店铺
     *
     * @apiParam {Number} id 店铺ID
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParamExample {json} Request-Example:
     *          {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *          }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "data": {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *              "ownerUserId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *       }
     *     }
     *
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 404 Not Found 若店铺未找到
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户尝试修改非自己管理店铺
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    /**
     * 根据店铺id修改店铺,返回修改后的信息
     *
     * @param shopId 店铺id
     * @param shop   店铺信息
     * @return 店铺信息
     */
    @PatchMapping("/shop/{id}")
    public DataMessage<Shop> updateShopByShopId(@PathVariable(value = "id") Long shopId, @RequestBody Shop shop) {
        return shopService.updateShopByShopId(shopId, shop);
    }

    /**
     * @api {get} /shop 获取当前用户名下的所有店铺
     * @apiName GetShop
     * @apiGroup 店铺
     *
     * @apiHeader {String} Accept application/json
     *
     * @apiParam {Number} pageNum 页数，从1开始
     * @apiParam {Number} pageSize 每页显示的数量
     *
     * @apiSuccess {Number} pageNum 页数，从1开始
     * @apiSuccess {Number} pageSize 每页显示的数量
     * @apiSuccess {Number} totalPage 共有多少页
     * @apiSuccess {Shop} data 店铺列表
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "pageNum": 1,
     *       "pageSize": 10,
     *       "totalPage": 5,
     *       "data": [
     *          {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *              "ownerUserId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *          },
     *          {
     *              ...
     *          }
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
     * 分页获得当前用户的所有店铺
     *
     * @param pageNum  页数，从1开始
     * @param pageSize 每页显示的数量
     * @return 店铺
     */
    @GetMapping("/shop")
    public PageMessage<List<Shop>> getShopsCurrentUser(@RequestParam(name = "pageNum") Integer pageNum,
                                                       @RequestParam(name = "pageSize") Integer pageSize) {
        return shopService.getShopsCurrentUser(pageNum, pageSize);
    }

    /**
     * @api {get} /shop/:id 获取指定ID的店铺
     * @apiName GetShopById
     * @apiGroup 店铺
     *
     * @apiHeader {String} Accept application/json
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 Created
     *     {
     *       "data": {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *              "ownerUserId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *       }
     *     }
     *
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 404 Not found 若店铺未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    /**
     * 根据店铺id获得店铺信息
     *
     * @param shopId 店铺id
     * @return 店铺信息
     */
    @GetMapping("/shop/{id}")
    public DataMessage<Shop> getShopByShopId(@PathVariable(value = "id") Long shopId) {
        return shopService.getShopByShopId(shopId);
    }
}

