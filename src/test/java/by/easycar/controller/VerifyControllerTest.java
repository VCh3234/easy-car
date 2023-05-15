package by.easycar.controller;


import by.easycar.model.user.UserPrincipal;
import by.easycar.service.verification.VerificationResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class VerifyControllerTest {

    private final HandlerMethodArgumentResolver userPrincipalResolver = new HandlerMethodArgumentResolver() {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.getParameterType().isAssignableFrom(UserPrincipal.class);
        }

        @Override
        public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                      @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
            return new UserPrincipal(1L, null, null, null);
        }
    };

    private MockMvc mockMvc;

    @Mock
    private VerificationResolver verificationResolver;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new VerifyController(verificationResolver))
                .setCustomArgumentResolvers(userPrincipalResolver)
                .build();
    }

    @Test
    public void sendCodeTest() throws Exception {
        mockMvc.perform(post("/verify/verify_sms"))
                .andExpect(status().isOk())
                .andReturn();
        verify(verificationResolver).sendMessage(1L, "verify_sms");
    }

    @Test
    public void verifyUserTest() throws Exception {
        mockMvc.perform(get("/verify/testCode"))
                .andExpect(status().isOk())
                .andReturn();
        verify(verificationResolver).verify("testCode");
    }
}