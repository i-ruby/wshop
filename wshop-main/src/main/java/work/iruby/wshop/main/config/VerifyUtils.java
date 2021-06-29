package work.iruby.wshop.main.config;

import work.iruby.wshop.common.exception.HttpException;

import java.util.regex.Pattern;

public class VerifyUtils {
    private static final String TEL_REGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
    private static final Pattern TEL_PATTERN = Pattern.compile(TEL_REGEX);

    /**
     * 校验手机号
     *
     * @param tel 手机号
     * @return 结果
     */
    public static boolean verifyTel(String tel) {
        if (tel == null) {
            return false;
        }
        return TEL_PATTERN.matcher(tel).matches();
    }

    /**
     * 校验手机号并抛出400HttpException
     *
     * @param tel 手机号
     */
    public static void verifyTelAndThrow400HttpException(String tel) {
        if (!verifyTel(tel)) {
            throw HttpException.badRequest("非法的手机号码");
        }
    }
}
