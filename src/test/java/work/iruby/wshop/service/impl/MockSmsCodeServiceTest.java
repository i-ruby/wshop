package work.iruby.wshop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import work.iruby.wshop.entity.WshopUser;
import work.iruby.wshop.service.SmsCodeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class MockSmsCodeServiceTest {

    @Mock
    private WshopUserServiceImpl wshopUserService;

    @Mock
    private SmsCodeService mockSmsCodeService;

    String right_tel = "18879628733";

    @BeforeEach
    void init() {
        mockSmsCodeService = new MockSmsCodeService(wshopUserService);
    }

    @Test
    void sendSmsCode() {
        WshopUser wshopUser = new WshopUser();
        wshopUser.setUserTel(right_tel);
        Mockito.when(wshopUserService.getOne(Mockito.any())).thenReturn(wshopUser);
        String code = mockSmsCodeService.sendSmsCode(right_tel);
        assertEquals(6, code.length());
    }

    @Test
    void getCorrectSmsCode() {
        WshopUser wshopUser = new WshopUser();
        wshopUser.setUserTel(right_tel);
        Mockito.when(wshopUserService.getOne(Mockito.any())).thenReturn(wshopUser);
        String code = mockSmsCodeService.getCorrectSmsCode(right_tel);
        assertNull(code);

        code = mockSmsCodeService.sendSmsCode(right_tel);
        assertEquals(code, mockSmsCodeService.getCorrectSmsCode(right_tel));
    }
}