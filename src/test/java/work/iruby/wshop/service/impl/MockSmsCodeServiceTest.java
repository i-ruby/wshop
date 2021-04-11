package work.iruby.wshop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import work.iruby.wshop.entity.User;
import work.iruby.wshop.service.SmsCodeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class MockSmsCodeServiceTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private SmsCodeService mockSmsCodeService;

    String right_tel = "18879628733";

    @BeforeEach
    void init() {
        mockSmsCodeService = new MockSmsCodeService(userService);
    }

    @Test
    void sendSmsCode() {
        User user = new User();
        user.setTel(right_tel);
        Mockito.when(userService.getOne(Mockito.any())).thenReturn(user);
        String code = mockSmsCodeService.sendSmsCode(right_tel);
        assertEquals(6, code.length());
    }

    @Test
    void getCorrectSmsCode() {
        User user = new User();
        user.setTel(right_tel);
        Mockito.when(userService.getOne(Mockito.any())).thenReturn(user);
        String code = mockSmsCodeService.getCorrectSmsCode(right_tel);
        assertNull(code);

        code = mockSmsCodeService.sendSmsCode(right_tel);
        assertEquals(code, mockSmsCodeService.getCorrectSmsCode(right_tel));
    }
}