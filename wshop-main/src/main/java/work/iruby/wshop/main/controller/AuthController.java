package work.iruby.wshop.main.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.iruby.wshop.common.dao.LoginResponse;
import work.iruby.wshop.common.dao.TelAndCode;
import work.iruby.wshop.common.entity.User;
import work.iruby.wshop.common.exception.HttpException;
import work.iruby.wshop.main.config.VerifyUtils;
import work.iruby.wshop.main.service.SmsCodeService;
import work.iruby.wshop.main.service.UserContext;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final SmsCodeService smsCodeService;

    public AuthController(SmsCodeService smsCodeService) {
        this.smsCodeService = smsCodeService;
    }

    /**
     * @api {post} /code 请求验证码
     * @apiName GetCode
     * @apiGroup 登录与鉴权
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParam {String} tel 手机号码
     * @apiParamExample {json} Request-Example:
     * {
     * "tel": "13812345678",
     * }
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * @apiError 400 Bad Request 若用户的请求包含错误
     * @apiErrorExample Error-Response:
     * HTTP/1.1 400 Bad Request
     * {
     * "message": "Bad Request"
     * }
     */
    /**
     * 根据电话号码获得验证码
     *
     * @param telAndCode 只取电话号码作为参数
     * @return String  验证码
     */
    @PostMapping("/code")
    public String code(@RequestBody TelAndCode telAndCode) {
        String tel = telAndCode.getTel();
        VerifyUtils.verifyTelAndThrow400HttpException(tel);
        return smsCodeService.sendSmsCode(tel);
    }

    /**
     * @api {post} /login 登录
     * @apiName Login
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {String} tel 手机号码
     * @apiParam {String} code 验证码
     * @apiParamExample {json} Request-Example:
     *          {
     *              "tel": "13812345678",
     *              "code": "000000"
     *          }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     * @apiError 400 Bad Request 若用户的请求包含错误
     * @apiError 403 Forbidden 若用户的验证码错误
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     */
    /**
     * 根据电话号码和验证码登录
     *
     * @param telAndCode {"tel": "13812345678","code": "000000"}
     */
    @PostMapping("/login")
    public void login(@RequestBody TelAndCode telAndCode) {
        UsernamePasswordToken token = new UsernamePasswordToken(telAndCode.getTel(), telAndCode.getCode());
        token.setRememberMe(true);
        VerifyUtils.verifyTelAndThrow400HttpException(telAndCode.getTel());
        try {
            SecurityUtils.getSubject().login(token);
        } catch (IncorrectCredentialsException e) {
            throw HttpException.forbidden("验证码错误");
        }
    }

    /**
     * @api {post} /logout 登出
     * @apiName Logout
     * @apiGroup 登录与鉴权
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * @apiError 401 Unauthorized 若用户未登录
     * @apiErrorExample Error-Response:
     * HTTP/1.1 401 Unauthorized
     * {
     * "message": "Unauthorized"
     * }
     */
    /**
     * 登出
     */
    @PostMapping("/logout")
    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * @api {get} /status 获取登录状态
     * @apiName Status
     * @apiGroup 登录与鉴权
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiSuccess {User} user 用户信息
     * @apiSuccess {Boolean} login 登录状态
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "login": true,
     * "user": {
     * "id": 123,
     * "name": "张三",
     * "tel": "13812345678",
     * "avatarUrl": "https://url",
     * "address": "北京市 西城区 100号",
     * }
     * }
     * @apiError 401 Unauthorized 若用户未登录
     * @apiErrorExample Error-Response:
     * HTTP/1.1 401 Unauthorized
     * {
     * "message": "Unauthorized"
     * }
     */
    /**
     * 获得登录状态
     *
     * @return 登录状态
     */
    @GetMapping("/status")
    public LoginResponse status() {
        User currentUser = UserContext.getCurrentUser();
        return new LoginResponse(currentUser != null, currentUser);
    }

}
