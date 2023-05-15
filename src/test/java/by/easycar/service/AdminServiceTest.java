package by.easycar.service;

import by.easycar.model.Payment;
import by.easycar.model.administration.Admin;
import by.easycar.model.administration.Moderation;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.dto.ModerationResponse;
import by.easycar.model.dto.user.UserInnerResponse;
import by.easycar.model.user.UserForAd;
import by.easycar.repository.AdminRepository;
import by.easycar.repository.ModerationRepository;
import by.easycar.service.mappers.ModerationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    private final List<Advertisement> advertisementList = new ArrayList<>();

    private final List<Moderation> moderations = new ArrayList<>();

    private final List<ModerationResponse> moderationResponses = new ArrayList<>();

    private final List<Payment> payments = new ArrayList<>();

    @Mock
    private AdvertisementService advertisementService;

    @Mock
    private ModerationRepository moderationRepository;

    @Mock
    private UserService userService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModerationMapper moderationMapper;

    @Mock
    private ImageService imageService;

    private Long userId;

    private Advertisement advertisement;

    private UserForAd userForAd;

    private Long adId;

    private AdminService adminService;

    private Admin admin;

    private Moderation moderation;

    private UserInnerResponse userInnerResponse;

    private ModerationResponse moderationResponse;

    @BeforeEach
    public void init() {
        adminService = new AdminService(advertisementService, moderationRepository, userService, paymentService, adminRepository, passwordEncoder, moderationMapper, imageService);
        admin = new Admin();
        admin.setId(2L);
        userId = 3L;
        adId = 1L;
        advertisement = new Advertisement();
        userForAd = new UserForAd();
        userForAd.setName("test");
        userForAd.setId(userId);
        advertisement.setId(adId);
        advertisement.setUser(userForAd);
        advertisementList.add(advertisement);
        moderation = new Moderation();
        moderation.setAdmin(admin);
        moderation.setMessage("Accept advertisement moderation");
        moderation.setAdvertisement(advertisement);
        userInnerResponse = new UserInnerResponse();
        moderations.add(moderation);
        moderationResponse = new ModerationResponse();
        moderationResponses.add(moderationResponse);
        payments.add(new Payment());
    }

    @Test
    public void getAllAdvertisementsNotModeratedTest() {
        when(advertisementService.getAllNotModeratedAdvertisement()).thenReturn(advertisementList);
        assertEquals(advertisementList, adminService.getAllAdvertisementsNotModerated());
    }

    @Test
    public void acceptAdvertisementTest() {
        when(advertisementService.getInnerAdvertisementByIdForAdmin(adId)).thenReturn(advertisement);
        adminService.acceptAdvertisement(adId, admin);
        verify(advertisementService).acceptModeration(adId);
        verify(moderationRepository).save(moderation);
    }

    @Test
    public void getUserInnerTest() {
        when(userService.getUserInner(userId)).thenReturn(userInnerResponse);
        assertEquals(userInnerResponse, adminService.getUserInner(userId));
    }

    @Test
    public void getInnerAdvertisementTest() {
        when(advertisementService.getInnerAdvertisementByIdForAdmin(adId)).thenReturn(advertisement);
        assertEquals(advertisement, adminService.getInnerAdvertisement(adId));
    }

    @Test
    public void getAllModerationTest() {
        when(moderationRepository.findAll()).thenReturn(moderations);
        when(moderationMapper.getModerationResponseFromModeration(moderation)).thenReturn(moderationResponse);
        assertEquals(moderationResponses, adminService.getAllModeration());
    }

    @Test
    public void deleteUserTest() {
        adminService.deleteUser(userId);
        verify(userService).deleteUserById(userId);
    }

    @Test
    public void deleteAdvertisementTest() {
        adminService.deleteAdvertisement(adId);
        verify(advertisementService).deleteForAdmin(adId);
    }

    @Test
    public void getAllPaymentsOfUserTest() {
        when(paymentService.getPaymentsOfUser(adId)).thenReturn(payments);
        assertEquals(payments, adminService.getAllPaymentsOfUser(adId));
    }

    @Test
    public void rejectAdvertisementTest() {
        when(advertisementService.getInnerAdvertisementByIdForAdmin(adId)).thenReturn(advertisement);
        moderation.setMessage("test");
        adminService.rejectAdvertisement(adId, admin, "test");
        verify(moderationRepository).save(moderation);
    }

    @Test
    public void getModerationOfUserTest() {
        when(userService.getUserForAdById(userId)).thenReturn(userForAd);
        when(moderationRepository.findModerationByAdvertisement_User(userForAd)).thenReturn(moderations);
        when(moderationMapper.getModerationResponseFromModeration(moderation)).thenReturn(moderationResponse);
        assertEquals(moderationResponses, adminService.getModerationOfUser(userId));
    }

    @Test
    public void getAllAdvertisementsOfUserTest() {
        when(advertisementService.getAllOfUser(userId)).thenReturn(advertisementList);
        adminService.getAllAdvertisementsOfUser(userId);
        assertEquals(advertisementList, adminService.getAllAdvertisementsOfUser(userId));
    }

    @Test
    public void deleteImageTest() throws IOException {
        adminService.deleteImage(adId, "test");
        verify(imageService).deleteImageForAdmin(adId, "test");
    }
}