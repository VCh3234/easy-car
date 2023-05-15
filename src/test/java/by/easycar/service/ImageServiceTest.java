package by.easycar.service;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.advertisement.ImageData;
import by.easycar.model.user.UserForAd;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class ImageServiceTest {

    private static final Path TEMP_TEST_PATH = Path.of("src/test/imagesTest");

    @Mock
    private AdvertisementService advertisementService;

    private ImageService imageService;

    private MockMultipartFile mockMultipartFile;

    private Long adId;

    private Advertisement advertisement;

    private UserForAd userForAd;

    private UUID testUuid;

    @BeforeAll
    public static void initAll() throws NoSuchFieldException, IllegalAccessException {
        Field field = ImageService.class.getDeclaredField("ROOT_PATH");
        field.setAccessible(true);
        field.set(field, TEMP_TEST_PATH);
        field.setAccessible(false);
    }

    @AfterAll
    public static void endAll() throws NoSuchFieldException, IllegalAccessException, IOException {
        Field field = ImageService.class.getDeclaredField("ROOT_PATH");
        field.setAccessible(true);
        field.set(field, Path.of("images"));
        field.setAccessible(false);
        Files.delete(TEMP_TEST_PATH);
    }

    @BeforeEach
    public void init() throws IOException {
        adId = 1L;
        mockMultipartFile = new MockMultipartFile("file", "content".getBytes());
        imageService = new ImageService(advertisementService);
        userForAd = new UserForAd();
        userForAd.setId(2L);
        advertisement = new Advertisement();
        advertisement.setId(1L);
        advertisement.setUser(userForAd);
        advertisement.setModerated(true);
        testUuid = UUID.randomUUID();
        Files.createDirectory(TEMP_TEST_PATH.resolve("1"));
    }

    @AfterEach
    public void after() {
        ImageService.deleteDir(1L);
    }

    @Test
    public void saveImageTest() throws IOException {
        when(advertisementService.getInnerAdvertisementById(adId, userForAd.getId())).thenReturn(advertisement);
        try (MockedStatic<UUID> imageServiceMockedStatic = Mockito.mockStatic(UUID.class)) {
            imageServiceMockedStatic.when(UUID::randomUUID).thenReturn(testUuid);
            imageService.postNewImages(mockMultipartFile, adId, userForAd.getId());
        }
        assertFalse(advertisement.isModerated());
        assertEquals(testUuid.toString(), advertisement.getImageData().getUuid1().toString());
        assertTrue(Files.exists(TEMP_TEST_PATH.resolve(adId + "/" + testUuid.toString() + ".jpg")));
        verify(advertisementService).saveChanges(advertisement);
    }

    @Test
    public void replaceImageTest() throws IOException {
        UUID newUuid = UUID.randomUUID();
        Files.copy(mockMultipartFile.getInputStream(), TEMP_TEST_PATH.resolve(Path.of(adId + "/" + testUuid.toString() + ".jpg")));
        advertisement.setImageData(new ImageData());
        advertisement.getImageData().setUuid1(testUuid);
        when(advertisementService.getInnerAdvertisementById(adId, userForAd.getId())).thenReturn(advertisement);
        try (MockedStatic<UUID> imageServiceMockedStatic = Mockito.mockStatic(UUID.class)) {
            imageServiceMockedStatic.when(UUID::randomUUID).thenReturn(newUuid);
            imageService.replaceImage(mockMultipartFile, testUuid.toString(), adId, userForAd.getId());
        }
        assertFalse(advertisement.isModerated());
        assertEquals(newUuid.toString(), advertisement.getImageData().getUuid1().toString());
        assertTrue(Files.exists(TEMP_TEST_PATH.resolve(Path.of(adId + "/" + newUuid + ".jpg"))));
        assertFalse(Files.exists(TEMP_TEST_PATH.resolve(Path.of(adId + "/" + testUuid + ".jpg"))));
        verify(advertisementService).saveChanges(advertisement);
    }

    @Test
    public void deleteImageTest() throws IOException {
        when(advertisementService.getInnerAdvertisementById(adId, userForAd.getId())).thenReturn(advertisement);
        Files.copy(mockMultipartFile.getInputStream(), TEMP_TEST_PATH.resolve(Path.of(adId + "/" + testUuid.toString() + ".jpg")));
        advertisement.setImageData(new ImageData());
        advertisement.getImageData().setUuid1(testUuid);
        imageService.deleteImage(testUuid.toString(), adId, userForAd.getId());
        assertFalse(Files.exists(TEMP_TEST_PATH.resolve(Path.of(testUuid + ".jpg"))));
        assertNull(advertisement.getImageData().getUuid1());
        verify(advertisementService).saveChanges(advertisement);
    }

    @Test
    public void deleteDirTest() throws IOException {
        Files.copy(mockMultipartFile.getInputStream(), TEMP_TEST_PATH.resolve(Path.of(adId + "/" + testUuid.toString() + ".jpg")));
        ImageService.deleteDir(adId);
        assertFalse(Files.exists(TEMP_TEST_PATH.resolve("/" + adId)));
    }

    @Test
    public void getImageTest() throws IOException {
        Files.copy(mockMultipartFile.getInputStream(), TEMP_TEST_PATH.resolve(Path.of(adId + "/" + testUuid.toString() + ".jpg")));
        byte[] result;
        try (MockedStatic<Files> imageServiceMockedStatic = Mockito.mockStatic(Files.class)) {
            imageServiceMockedStatic.when(() -> Files.readAllBytes(TEMP_TEST_PATH.resolve(Path.of(adId + "/" + testUuid.toString() + ".jpg")))).thenReturn(new byte[]{1, 2, 3});
            result = imageService.getImage(adId, testUuid.toString());
        }
        assertArrayEquals(new byte[]{1, 2, 3}, result);
    }
}