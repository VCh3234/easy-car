package by.easycar.controller;

import by.easycar.model.Payment;
import by.easycar.model.administration.Admin;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.dto.ModerationResponse;
import by.easycar.model.dto.user.UserInnerResponse;
import by.easycar.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    private final List<Advertisement> advertisements = new ArrayList<>();

    private final List<Payment> paymentsResponse = new ArrayList<>();

    private final List<ModerationResponse> moderationResponses = new ArrayList<>();

    private final HandlerMethodArgumentResolver adminResolver = new HandlerMethodArgumentResolver() {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.getParameterType().isAssignableFrom(Admin.class);
        }

        @Override
        public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                      @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
            return new Admin(1L, null, null, null);
        }
    };

    private MockMvc mockMvc;

    @Mock
    private AdminService adminService;

    private Long adId;

    private Long userId;

    private ObjectWriter jsonMapper;

    private Advertisement advertisement;

    private UserInnerResponse userInnerResponse;

    @BeforeEach
    public void init() {
        ModerationResponse moderation = new ModerationResponse();
        moderation.setId(1L);
        moderationResponses.add(moderation);
        adId = 1L;
        userId = 1L;
        paymentsResponse.add(new Payment());
        advertisement = new Advertisement();
        advertisement.setId(adId);
        advertisements.add(advertisement);
        userInnerResponse = new UserInnerResponse();
        userInnerResponse.setName("test");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        jsonMapper = objectMapper.writerWithDefaultPrettyPrinter();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AdminController(adminService))
                .setCustomArgumentResolvers(adminResolver)
                .build();
    }

    @Test
    public void getAdvertisementsForModerationTest() throws Exception {
        when(adminService.getAllAdvertisementsNotModerated()).thenReturn(advertisements);
        mockMvc.perform(get("/admin/advertisements"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.writeValueAsString(advertisements)))
                .andReturn();
        verify(adminService, times(1)).getAllAdvertisementsNotModerated();
    }

    @Test
    public void getInnerUserTest() throws Exception {
        when(adminService.getUserInner(userId)).thenReturn(userInnerResponse);
        mockMvc.perform(get("/admin/get-user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.writeValueAsString(userInnerResponse)))
                .andReturn();
        verify(adminService, times(1)).getUserInner(userId);
    }

    @Test
    public void getInnerAdvertisementTest() throws Exception {
        when(adminService.getInnerAdvertisement(adId)).thenReturn(advertisement);
        mockMvc.perform(get("/admin/advertisement/{adId}", adId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.writeValueAsString(advertisement)))
                .andReturn();
        verify(adminService, times(1)).getInnerAdvertisement(adId);
    }

    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/admin/user").param("userId", "1"))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(adminService, times(1)).deleteUser(userId);
    }

    @Test
    public void deleteAdvertisementTest() throws Exception {
        mockMvc.perform(delete("/admin/advertisement").param("adId", "1"))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(adminService, times(1)).deleteAdvertisement(adId);
    }

    @Test
    public void getAllPaymentsOfUserTest() throws Exception {
        when(adminService.getAllPaymentsOfUser(userId)).thenReturn(paymentsResponse);
        mockMvc.perform(get("/admin/payments/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.writeValueAsString(paymentsResponse)))
                .andReturn();
        verify(adminService, times(1)).getAllPaymentsOfUser(userId);
    }

    @Test
    public void acceptAdvertisementTest() throws Exception {
        Admin admin = (Admin) adminResolver.resolveArgument(null, null, null, null);
        mockMvc.perform(put("/admin/accept-advertisement").param("adId", "1"))
                .andExpect(status().isOk())
                .andReturn();
        verify(adminService, times(1)).acceptAdvertisement(adId, admin);
    }

    @Test
    public void rejectAdvertisementTest() throws Exception {
        Admin admin = (Admin) adminResolver.resolveArgument(null, null, null, null);
        mockMvc.perform(put("/admin/reject-advertisement").param("adId", "1").param("message", "test"))
                .andExpect(status().isOk())
                .andReturn();
        verify(adminService, times(1)).rejectAdvertisement(adId, admin, "test");
    }

    @Test
    public void addNewAdminTest() throws Exception {
        Admin admin = new Admin();
        mockMvc.perform(post("/admin/add").contentType(MediaType.APPLICATION_JSON).content(jsonMapper.writeValueAsString(admin)))
                .andExpect(status().isOk())
                .andReturn();
        verify(adminService, times(1)).saveNewAdmin(admin);
    }

    @Test
    public void getAllAdvertisementsOfUserTest() throws Exception {
        when(adminService.getAllAdvertisementsOfUser(userId)).thenReturn(advertisements);
        mockMvc.perform(get("/admin/advertisements/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.writeValueAsString(advertisements)))
                .andReturn();
        verify(adminService, times(1)).getAllAdvertisementsOfUser(userId);
    }

    @Test
    public void getAllModerationByUserTest() throws Exception {
        when(adminService.getModerationOfUser(userId)).thenReturn(moderationResponses);
        mockMvc.perform(get("/admin/moderation/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.writeValueAsString(moderationResponses)))
                .andReturn();
        verify(adminService, times(1)).getModerationOfUser(userId);
    }

    @Test
    public void getAllModerationTest() throws Exception {
        when(adminService.getAllModeration()).thenReturn(moderationResponses);
        mockMvc.perform(get("/admin/moderation"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.writeValueAsString(moderationResponses)))
                .andReturn();
        verify(adminService, times(1)).getAllModeration();
    }

    @Test
    public void deleteImageTest() throws Exception {
        mockMvc.perform(delete("/admin/image/{adId}/{image}", adId, "uuid"))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(adminService, times(1)).deleteImage(adId, "uuid");
    }
}