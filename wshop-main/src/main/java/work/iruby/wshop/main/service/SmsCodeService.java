package work.iruby.wshop.main.service;

public interface SmsCodeService {
    String sendSmsCode(String tel);

    String getCorrectSmsCode(String tel);
}
