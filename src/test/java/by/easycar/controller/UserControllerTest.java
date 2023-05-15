package by.easycar.controller;


import by.easycar.model.dto.user.PasswordRequest;
import by.easycar.model.dto.user.UserInnerResponse;
import by.easycar.model.dto.user.UserRegisterRequest;
import by.easycar.model.dto.user.UserRequest;
import by.easycar.model.user.UserPrincipal;
import by.easycar.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserPrincipal userPrincipal;

    private Long userId;

    private ObjectWriter jsonMapper;

    private PasswordRequest passwordRequest;

    private UserRequest userRequest;

    private UserRegisterRequest userRegisterRequest;

    private UserInnerResponse userInnerResponse;

    @BeforeEach
    public void init() {
        userInnerResponse = new UserInnerResponse();
        userInnerResponse.setId(userId);
        userInnerResponse.setCreationDate(LocalDate.now());
        userInnerResponse.setUpdateTime(LocalDateTime.now());
        userInnerResponse.setName("testName");
        userInnerResponse.setPhoneNumber("295658987");
        userInnerResponse.setEmail("test@email.ru");
        userInnerResponse.setUps(0);
        userInnerResponse.setVerifiedByEmail(false);
        userInnerResponse.setVerifiedByPhone(false);
        passwordRequest = new PasswordRequest();
        passwordRequest.setPassword("testPassword1");
        userRequest = new UserRequest();
        userRequest.setPhoneNumber("295658987");
        userRequest.setEmail("testEmail@foo.ru");
        userRequest.setName("testName");
        userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setPasswordRequest(passwordRequest);
        userRegisterRequest.setUserRequest(userRequest);
        jsonMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
        userId = 1L;
        userPrincipal = new UserPrincipal(userId, "test", "test@email.ru", new ArrayList<>(List.of((GrantedAuthority) () -> "USER")));
    }

    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/users").with(user(userPrincipal)))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(userService, times(1)).deleteUserById(userId);
    }

    @Test
    public void getUserInnerTest() throws Exception {
        when(userService.getUserInner(userId)).thenReturn(userInnerResponse);
        mockMvc.perform(get("/users").with(user(userPrincipal)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        verify(userService, times(1)).getUserInner(userId);
    }

    @Test
    public void updatePasswordUserTest() throws Exception {
        mockMvc.perform(put("/users/update-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(passwordRequest))
                        .with(user(userPrincipal)))
                .andExpect(status().isOk())
                .andReturn();
        verify(userService, times(1)).updatePassword(passwordRequest.getPassword(), userId);
    }

    @Test
    public void updateUserTest() throws Exception {
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(userRequest))
                        .with(user(userPrincipal)))
                .andExpect(status().isOk())
                .andReturn();
        verify(userService, times(1)).updateUser(userRequest, userId);
    }

    @Test
    public void registerUserTest() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(userRegisterRequest))
                        .with(user(userPrincipal)))
                .andExpect(status().isCreated())
                .andReturn();
        verify(userService, times(1)).saveNewUser(userRegisterRequest);
    }
}