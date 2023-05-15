package by.easycar.controller;

import by.easycar.model.user.UserPrincipal;
import by.easycar.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ImageService imageService;

    private Long adId;

    private Long userId;

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

    private MockMultipartFile file;

    @BeforeEach
    public void init() {
        userId = 1L;
        adId = 1L;
        file = new MockMultipartFile("file", "testFile".getBytes());
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ImageController(imageService))
                .setCustomArgumentResolvers(userPrincipalResolver)
                .build();
    }

    @Test
    public void postNewImageTest() throws Exception {
        mockMvc.perform(multipart(HttpMethod.POST, "/images/{adId}", adId)
                        .file(file))
                .andExpect(status().isOk())
                .andReturn();
        verify(imageService, times(1)).postNewImages(file, adId, userId);
    }

    @Test
    public void replaceImageTest() throws Exception {
        mockMvc.perform(multipart(HttpMethod.PUT, "/images/{adId}", adId)
                        .file(file)
                        .param("oldImage", "UUID"))
                .andExpect(status().isOk())
                .andReturn();
        verify(imageService, times(1)).replaceImage(file, "UUID", adId, userId);
    }

    @Test
    public void deleteImageTest() throws Exception {
        mockMvc.perform(multipart(HttpMethod.DELETE, "/images/{adId}", adId)
                        .param("oldImage", "UUID"))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(imageService, times(1)).deleteImage("UUID", adId, userId);
    }

    @Test
    public void getImage() throws Exception {
        mockMvc.perform(multipart(HttpMethod.GET, "/images/{adId}", adId)
                        .param("uuid", "UUID"))
                .andExpect(status().isOk())
                .andReturn();
        verify(imageService, times(1)).getImage(adId, "UUID");
    }
}