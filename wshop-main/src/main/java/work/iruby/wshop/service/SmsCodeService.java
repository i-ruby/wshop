package work.iruby.wshop.service;

public interface SmsCodeService {
    String sendSmsCode(String tel);

    String getCorrectSmsCode(String tel);
}
