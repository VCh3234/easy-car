package by.easycar.controller;

import by.easycar.model.Payment;
import by.easycar.model.user.UserPrincipal;
import by.easycar.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    private Long userId;

    private final List<Payment> paymentsResponse = new ArrayList<>();

    private ObjectWriter jsonMapper;

    private final HashMap<String, String> paymentRequest = new HashMap<>();

    private final HandlerMethodArgumentResolver userPrincipalResolver = new HandlerMethodArgumentResolver() {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.getParameterType().isAssignableFrom(UserPrincipal.class);
        }

        @Override
        public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                      @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
            return new UserPrincipal(userId, null, null, null);
        }
    };

    @BeforeEach
    public void init() {
        jsonMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
        paymentsResponse.add(new Payment());
        userId = 1L;
        paymentRequest.put("key", "value");
        mockMvc = MockMvcBuilders
                .standaloneSetup(new PaymentController(paymentService))
                .setCustomArgumentResolvers(userPrincipalResolver)
                .build();
    }

    @Test
    public void getTokenTest() throws Exception {
            when(paymentService.getToken(paymentRequest)).thenReturn("testToken");
            mockMvc.perform(post("/pays/get-token-for-demonstration").contentType(MediaType.APPLICATION_JSON).content("{\"key\":\"value\"}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("testToken"))
                    .andReturn();
            verify(paymentService, times(1)).getToken(paymentRequest);
    }

    @Test
    public void getPaymentsOfUser() throws Exception {
        when(paymentService.getPaymentsOfUser(userId)).thenReturn(paymentsResponse);
        mockMvc.perform(get("/pays"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.writeValueAsString(paymentsResponse)))
                .andReturn();
        verify(paymentService, times(1)).getPaymentsOfUser(userId);
    }

    @Test
    public void depositTest() throws Exception {
        mockMvc.perform(post("/pays").content("testToken"))
                .andExpect(status().isOk())
                .andReturn();
        verify(paymentService, times(1)).verifyAndMakePay("testToken");
    }
}