package work.iruby.wshop.main.config;

import work.iruby.wshop.common.exception.HttpException;

import java.util.regex.Pattern;

public class VerifyUtils {
    private final static String TEL_REGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
    private final static Pattern TEL_PATTERN = Pattern.compile(TEL_REGEX);

    public static boolean verifyTel(String tel) {
        if (tel == null) {
            return false;
        }
        return TEL_PATTERN.matcher(tel).matches();
    }

    public static void verifyTelAndThrow400HttpException(String tel) {
        if (!verifyTel(tel)) {
            throw HttpException.badRequest("非法的手机号码");
        }
    }
}
