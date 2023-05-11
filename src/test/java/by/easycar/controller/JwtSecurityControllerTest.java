package by.easycar.controller;

import by.easycar.model.dto.AuthRequest;
import by.easycar.service.security.admin.AdminJwtAuthenticationService;
import by.easycar.service.security.admin.AdminSecurityService;
import by.easycar.service.security.user.UserJwtAuthenticationService;
import by.easycar.service.security.user.UserSecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class JwtSecurityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserSecurityService userSecurityService;

    @Mock
    private UserJwtAuthenticationService userJwtAuthenticationService;

    @Mock
    private AdminSecurityService adminSecurityService;

    @Mock
    private AdminJwtAuthenticationService adminJwtAuthenticationService;

    private Long userId;

    private ObjectWriter jsonMapper;

    private AuthRequest authRequest;

    @BeforeEach
    public void init() {
        authRequest = new AuthRequest("testEmail@email.ru", "testPassword");
        jsonMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
        userId = 1L;
        mockMvc = MockMvcBuilders
                .standaloneSetup(new JwtSecurityController(
                        userJwtAuthenticationService,
                        adminJwtAuthenticationService,
                        adminSecurityService,
                        userSecurityService))
                .build();
    }

    @Test
    public void getJwtTokenForUserTest() throws Exception {
        when(userSecurityService.authenticateUser(authRequest.getName(), authRequest.getPassword())).thenReturn(true);
        when(userJwtAuthenticationService.getToken(authRequest.getName())).thenReturn("testToken");
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("testToken"))
                .andReturn();
        verify(userSecurityService, times(1)).authenticateUser(authRequest.getName(), authRequest.getPassword());
        verify(userJwtAuthenticationService, times(1)).getToken(authRequest.getName());
    }

    @Test
    public void getJwtTokenForAdminTest() throws Exception {
        when(adminSecurityService.authenticateUser(authRequest.getName(), authRequest.getPassword())).thenReturn(true);
        when(adminJwtAuthenticationService.getToken(authRequest.getName())).thenReturn("testToken");
        mockMvc.perform(post("/auth/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("testToken"))
                .andReturn();
        verify(adminSecurityService, times(1)).authenticateUser(authRequest.getName(), authRequest.getPassword());
        verify(adminJwtAuthenticationService, times(1)).getToken(authRequest.getName());
    }
}