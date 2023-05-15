package by.easycar.service;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.advertisement.Vehicle;
import by.easycar.model.dto.AdvertisementRequest;
import by.easycar.model.user.UserForAd;
import by.easycar.model.user.UserPrivate;
import by.easycar.repository.AdvertisementRepository;
import by.easycar.repository.VehicleRepository;
import by.easycar.service.mappers.AdvertisementMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceTest {

    private final List<Advertisement> advertisementList = new ArrayList<>();

    @Mock
    private UserService userService;

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private AdvertisementMapper advertisementMapper;

    private UserPrivate userPrivate;

    private Long userId;

    private AdvertisementRequest advertisementRequest;

    private Advertisement advertisement;

    private UserForAd userForAd;

    private AdvertisementService advertisementService;

    private Vehicle vehicle;

    private Long adId;

    @BeforeEach
    public void init() {
        advertisementService = new AdvertisementService(advertisementRepository, vehicleRepository, userService, advertisementMapper);
        userId = 1L;
        adId = 1L;
        userPrivate = new UserPrivate();
        userPrivate.setId(userId);
        userPrivate.setName("test");
        userPrivate.setVerifiedByEmail(true);
        advertisementRequest = new AdvertisementRequest();
        advertisement = new Advertisement();
        userForAd = new UserForAd();
        userForAd.setName("test");
        userForAd.setId(userId);
        vehicle = new Vehicle();
        vehicle.setBrand("test");
        advertisement.setVehicle(vehicle);
        advertisement.setId(adId);
        advertisement.setUser(userForAd);
        advertisementList.add(advertisement);
        userPrivate.setAdvertisements(advertisementList);
        userPrivate.setUps(10);
    }

    @Test
    public void saveNewAdTest() {
        when(userService.getById(userId)).thenReturn(userPrivate);
        when(advertisementMapper.getAdvertisementFromAdvertisementRequest(advertisementRequest)).thenReturn(advertisement);
        when(userService.getUserForAdFromUserPrivate(userPrivate)).thenReturn(userForAd);
        Example<Vehicle> vehicleExample = Example.of(vehicle);
        when(vehicleRepository.findOne(vehicleExample)).thenReturn(Optional.of(vehicle));
        advertisementService.saveNewAd(advertisementRequest, userId);
        assertEquals(advertisement.getUser(), userForAd);
        assertEquals(advertisement.getVehicle(), vehicle);
        verify(advertisementRepository).save(advertisement);
    }

    @Test
    public void updateTest() {
        advertisement.setModerated(true);
        when(advertisementRepository.findById(userId)).thenReturn(Optional.of(advertisement));
        when(advertisementMapper.getVehicleFromAdvertisementRequest(advertisementRequest)).thenReturn(vehicle);
        advertisementService.update(adId, advertisementRequest, userId);
        assertFalse(advertisement.isModerated());
        verify(advertisementMapper).setUpdates(advertisement, advertisementRequest);
    }

    @Test
    public void getAllNotModeratedAdvertisementTest() {
        when(advertisementRepository.findAllByModerated(false)).thenReturn(advertisementList);
        assertEquals(advertisementList, advertisementService.getAllNotModeratedAdvertisement());
    }

    @Test
    public void deleteTest() {
        when(advertisementRepository.findById(adId)).thenReturn(Optional.of(advertisement));
        try (MockedStatic<ImageService> imageServiceMockedStatic = Mockito.mockStatic(ImageService.class)) {
            advertisementService.delete(adId, userId);
            imageServiceMockedStatic.verify(() -> ImageService.deleteDir(adId));
        }
        verify(advertisementRepository).deleteById(adId);
    }

    @Test
    public void getAllOfUserTest() {
        when(userService.getById(userId)).thenReturn(userPrivate);
        assertEquals(advertisementList, advertisementService.getAllOfUser(userId));
    }

    @Test
    public void getInnerAdvertisementByIdTest() {
        when(advertisementRepository.findById(adId)).thenReturn(Optional.of(advertisement));
        assertEquals(advertisement, advertisementService.getInnerAdvertisementById(adId, userId));
    }

    @Test
    public void getPublicByIdTest() {
        when(advertisementRepository.findByIdAndModerated(adId, true)).thenReturn(Optional.of(advertisement));
        assertEquals(advertisement, advertisementService.getPublicById(adId));
    }

    @Test
    public void acceptModerationTest() {
        advertisementService.acceptModeration(adId);
        verify(advertisementRepository).acceptModeration(adId);
    }

    @Test
    public void saveChangesTest() {
        advertisementService.saveChanges(advertisement);
        verify(advertisementRepository).save(advertisement);
    }

    @Test
    public void getAllModeratedAdvertisementOrderedTest() {
        when(advertisementRepository.findAllByModeratedOrderByUpTimeDesc(true)).thenReturn(advertisementList);
        assertEquals(advertisementList, advertisementService.getAllModeratedAdvertisementOrdered());
    }

    @Test
    public void upAdvertisementTest() {
        when(userService.getById(userId)).thenReturn(userPrivate);
        when(advertisementRepository.findByIdAndModerated(adId, true)).thenReturn(Optional.of(advertisement));
        advertisementService.upAdvertisement(adId, userId);
        assertEquals(9, userPrivate.getUps());
        assertTrue(advertisement.getUpTime().isBefore(LocalDateTime.now().plusMinutes(1)));
        verify(advertisementRepository).save(advertisement);
        verify(userService).saveChanges(userPrivate);
    }

    @Test
    public void getInnerAdvertisementByIdForAdminTest() {
        when(advertisementRepository.findById(adId)).thenReturn(Optional.of(advertisement));
        assertEquals(advertisement, advertisementService.getInnerAdvertisementByIdForAdmin(adId));
    }

    @Test
    public void deleteForAdminTest() {
        try (MockedStatic<ImageService> imageServiceMockedStatic = Mockito.mockStatic(ImageService.class)) {
            advertisementService.deleteForAdmin(adId);
            imageServiceMockedStatic.verify(() -> ImageService.deleteDir(adId));
        }
        verify(advertisementRepository).deleteById(adId);
    }
}