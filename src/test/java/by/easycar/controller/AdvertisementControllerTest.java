package by.easycar.controller;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.dto.AdvertisementRequest;
import by.easycar.model.dto.ModerationResponse;
import by.easycar.model.dto.SearchParams;
import by.easycar.model.user.UserPrincipal;
import by.easycar.service.AdminService;
import by.easycar.service.AdvertisementService;
import by.easycar.service.search.SearchAdvertisementService;
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
public class AdvertisementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdvertisementService advertisementService;

    @Mock
    private SearchAdvertisementService searchAdvertisementService;

    @Mock
    private AdminService adminService;

    private Long adId;

    private Long userId;

    private final List<Advertisement> advertisements = new ArrayList<>();

    private final List<ModerationResponse> moderationResponses = new ArrayList<>();

    private final List<SearchParams> searchParams = new ArrayList<>();

    private ObjectWriter jsonMapper;

    private AdvertisementRequest advertisementRequest;

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
        Advertisement advertisement = new Advertisement();
        advertisement.setId(adId);
        ModerationResponse moderation = new ModerationResponse();
        moderation.setId(1L);
        moderationResponses.add(moderation);
        SearchParams searchParams = new SearchParams();
        searchParams.setKey("testKey");
        this.searchParams.add(searchParams);
        advertisementRequest = new AdvertisementRequest();
        advertisementRequest.setPrice(2000);
        advertisementRequest.setVinNumber("qwertqwertqwertqw");
        advertisementRequest.setDescription("test");
        advertisementRequest.setRegion("test");
        advertisementRequest.setMileage(2000);
        advertisementRequest.setEngineCapacity(2000);
        advertisementRequest.setEngineType("test");
        advertisementRequest.setTransmissionType("test");
        advertisementRequest.setBrand("test");
        advertisementRequest.setModel("test");
        advertisementRequest.setGeneration("test");
        advertisementRequest.setBodyType("test");
        advertisementRequest.setCarYear(2000);
        userId = 1L;
        adId = 1L;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        jsonMapper = objectMapper.writerWithDefaultPrettyPrinter();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AdvertisementController(advertisementService, searchAdvertisementService, adminService))
                .setCustomArgumentResolvers(userPrincipalResolver)
                .build();
    }

    @Test
    public void getPublicAdvertisementTest() throws Exception {
        when(advertisementService.getAllModeratedAdvertisementOrdered()).thenReturn(advertisements);
        mockMvc.perform(get("/ads"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.writeValueAsString(advertisements)))
                .andReturn();
        verify(advertisementService, times(1)).getAllModeratedAdvertisementOrdered();
    }

    @Test
    public void getModerationOfUserTest() throws Exception {
        UserPrincipal userPrincipal = (UserPrincipal) userPrincipalResolver.resolveArgument(null, null, null, null);
        assert userPrincipal != null;
        when(adminService.getModerationOfUser(userPrincipal)).thenReturn(moderationResponses);
        mockMvc.perform(get("/ads/moderation"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.writeValueAsString(moderationResponses)))
                .andReturn();
        verify(adminService, times(1)).getModerationOfUser(userPrincipal);
    }

    @Test
    public void getAdvertisementsWithFilterTest() throws Exception {
        when(searchAdvertisementService.getAllByParams(this.searchParams)).thenReturn(advertisements);
        mockMvc.perform(post("/ads/search")
                        .content(jsonMapper.writeValueAsString(this.searchParams))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.writeValueAsString(advertisements)))
                .andReturn();
        verify(searchAdvertisementService, times(1)).getAllByParams(this.searchParams);
    }

    @Test
    public void getInnerAdvertisementsTest() throws Exception {
        when(advertisementService.getAllOfUser(userId)).thenReturn(advertisements);
        mockMvc.perform(get("/ads/my-ads"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMapper.writeValueAsString(advertisements)))
                .andReturn();
        verify(advertisementService, times(1)).getAllOfUser(userId);
    }

    @Test
    public void postNewAdvertisementTest() throws Exception {
        mockMvc.perform(post("/ads")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(advertisementRequest)))
                .andExpect(status().isOk())
                .andReturn();
        verify(advertisementService, times(1)).saveNewAd(advertisementRequest, userId);
    }

    @Test
    public void updateAdvertisementTest() throws Exception {
        mockMvc.perform(put("/ads/{adId}", adId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(advertisementRequest)))
                .andExpect(status().isOk())
                .andReturn();
        verify(advertisementService, times(1)).update(adId, advertisementRequest, userId);
    }

    @Test
    public void deleteAdvertisementTest() throws Exception {
        mockMvc.perform(delete("/ads/{adId}", adId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(advertisementRequest)))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(advertisementService, times(1)).delete(adId, userId);
    }

    @Test
    public void upAdvertisementTest() throws Exception {
        mockMvc.perform(put("/ads/up/{adId}", adId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(advertisementRequest)))
                .andExpect(status().isOk())
                .andReturn();
        verify(advertisementService, times(1)).upAdvertisement(adId, userId);
    }
}