package by.easycar.service;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.dto.user.PasswordRequest;
import by.easycar.model.dto.user.UserInnerResponse;
import by.easycar.model.dto.user.UserRegisterRequest;
import by.easycar.model.dto.user.UserRequest;
import by.easycar.model.user.UserForAd;
import by.easycar.model.user.UserPrivate;
import by.easycar.repository.UserForAdRepository;
import by.easycar.repository.UserRepository;
import by.easycar.service.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserForAdRepository userForAdRepository;

    private UserService userService;

    private Long userId;

    private UserPrivate userPrivate;

    private UserInnerResponse userInnerResponse;

    private UserRegisterRequest userRegisterRequest;

    private UserRequest userRequest;

    private PasswordRequest passwordRequest;

    private UserForAd userForAd;

    @BeforeEach
    public void init() {
        userId = 1L;
        userService = new UserService(userRepository, userMapper, passwordEncoder, userForAdRepository);
        userPrivate = new UserPrivate();
        userPrivate.setPassword("testPass");
        userPrivate.setId(userId);
        Advertisement advertisement = new Advertisement();
        advertisement.setId(1L);
        userRegisterRequest = new UserRegisterRequest();
        userRequest = new UserRequest();
        passwordRequest = new PasswordRequest();
        passwordRequest.setPassword("test");
        userRegisterRequest.setUserRequest(userRequest);
        userRegisterRequest.setPasswordRequest(passwordRequest);
        userPrivate.getAdvertisements().add(advertisement);
        userInnerResponse = new UserInnerResponse();
        userForAd = new UserForAd();

    }

    @Test
    public void getByIdTest() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userPrivate));
        UserPrivate result = userService.getById(userId);
        assertEquals(userPrivate, result);
    }

    @Test
    public void getUserInnerTest() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userPrivate));
        when(userMapper.getUserInnerFromUserPrivate(userPrivate)).thenReturn(userInnerResponse);
        UserInnerResponse result = userService.getUserInner(userId);
        assertEquals(userInnerResponse, result);
    }

    @Test
    public void deleteUserByIdTest() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userPrivate));
        try (MockedStatic<ImageService> imageServiceMockedStatic = Mockito.mockStatic(ImageService.class)) {
            userService.deleteUserById(userId);
            imageServiceMockedStatic.verify((() -> ImageService.deleteDir(userPrivate.getAdvertisements().get(0).getId())),
                    times(1));
        }
        verify(userRepository).deleteById(userId);
    }

    @Test
    public void saveNewUserTest() {
        when(passwordEncoder.encode(passwordRequest.getPassword())).thenReturn("testPass");
        when(userMapper.getUserPrivateFromUserRegisterRequest(userRegisterRequest.getUserRequest(), "testPass")).thenReturn(userPrivate);
        userService.saveNewUser(userRegisterRequest);
        assertEquals("testPass", userPrivate.getPassword());
        verify(userRepository).save(userPrivate);
    }

    @Test
    public void updateUserTest() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userPrivate));
        userPrivate.setVerifiedByEmail(true);
        userPrivate.setVerifiedByEmail(true);
        userPrivate.setEmail("test");
        userPrivate.setPhoneNumber("test");
        userPrivate.setName("test");
        userRequest.setName("changed");
        userRequest.setEmail("changed");
        userRequest.setPhoneNumber("changed");
        userService.updateUser(userRequest, userId);
        assertFalse(userPrivate.isVerifiedByEmail());
        assertFalse(userPrivate.isVerifiedByPhone());
        assertEquals(userRequest.getName(), userPrivate.getName());
        assertEquals(userRequest.getPhoneNumber(), userPrivate.getPhoneNumber());
        assertEquals(userRequest.getEmail(), userPrivate.getEmail());
        verify(userRepository).save(userPrivate);
    }

    @Test
    public void getUserForAdFromUserPrivateTest() {
        when(userMapper.getUserForAdFromUserPrivate(userPrivate)).thenReturn(userForAd);
        assertEquals(userForAd, userService.getUserForAdFromUserPrivate(userPrivate));
        verify(userMapper).getUserForAdFromUserPrivate(userPrivate);
    }

    @Test
    public void updatePasswordTest() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userPrivate));
        when(passwordEncoder.encode("test")).thenReturn("testPass");
        userService.updatePassword("test", userPrivate.getId());
        assertEquals("testPass", userPrivate.getPassword());
        verify(userRepository).save(userPrivate);
    }

    @Test
    public void setVerifiedByPhoneTest() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userPrivate));
        userPrivate.setVerifiedByPhone(false);
        userService.setVerifiedByPhone(userPrivate.getId());
        assertTrue(userPrivate.isVerifiedByPhone());
        verify(userRepository).save(userPrivate);
    }

    @Test
    public void setVerifiedByEmail() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userPrivate));
        userPrivate.setVerifiedByEmail(false);
        userService.setVerifiedByEmail(userPrivate.getId());
        assertTrue(userPrivate.isVerifiedByEmail());
        verify(userRepository).save(userPrivate);
    }

    @Test
    public void saveChangesTest() {
        userService.saveChanges(userPrivate);
        verify(userRepository).save(userPrivate);
    }

    @Test
    public void getUserForAdByIdTest() {
        when(userForAdRepository.findById(userId)).thenReturn(Optional.of(userForAd));
        assertEquals(userForAd, userService.getUserForAdById(userId));
    }
}