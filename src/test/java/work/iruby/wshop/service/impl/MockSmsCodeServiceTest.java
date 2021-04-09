package work.iruby.wshop.service.impl;

import org.junit.jupiter.api.Test;
import work.iruby.wshop.service.SmsCodeService;

import static org.junit.jupiter.api.Assertions.*;


class MockSmsCodeServiceTest {

    private SmsCodeService mockSmsCodeService = new MockSmsCodeService();
    String right_tel = "18879628733";
    String err_tel = "e1887962sas";

    @Test
    void sendSmsCode() {
        String code = mockSmsCodeService.sendSmsCode(right_tel);
        assertEquals(6, code.length());
    }

    @Test
    void getCorrectSmsCode() {
        String code = mockSmsCodeService.getCorrectSmsCode(right_tel);
        assertNull(code);

        mockSmsCodeService.sendSmsCode(right_tel);
        code = mockSmsCodeService.getCorrectSmsCode(right_tel);
        assertEquals(6, code.length());
    }
}