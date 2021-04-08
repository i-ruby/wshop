package work.iruby.wshop.service.impl;

import org.springframework.stereotype.Service;
import work.iruby.wshop.service.SmsCodeService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MockSmsCodeService implements SmsCodeService {
    Map<String, String> telAndCode = new ConcurrentHashMap<>();

    @Override
    public String sendSmsCode(String tel) {
        String code = "000000";
        telAndCode.put(tel, code);
        return code;
    }

    @Override
    public String getCorrectSmsCode(String tel) {
        return telAndCode.get(tel);
    }
}
