package by.easycar.model.advertisement;

import by.easycar.exceptions.advertisement.images.CantFindOldUuidException;
import by.easycar.exceptions.advertisement.images.FullImageDataException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.UUID;

@Data
@Embeddable
public class ImageData {

    @Column(name = "im_name_1")
    private UUID uuid1;

    @Column(name = "im_name_2")
    private UUID uuid2;

    @Column(name = "im_name_3")
    private UUID uuid3;

    @Column(name = "im_name_4")
    private UUID uuid4;

    public void postNewImage(UUID uuid) {
        if (uuid1 == null) {
            uuid1 = uuid;
        } else if (uuid2 == null) {
            uuid2 = uuid;
        } else if (uuid3 == null) {
            uuid3 = uuid;
        } else if (uuid4 == null) {
            uuid4 = uuid;
        } else {
            throw new FullImageDataException("ImageData is full you must replace some image.");
        }
    }

    public void replace(String oldUuid, UUID newUuid) {
        if (uuid1.toString().equals(oldUuid)) {
            uuid1 = newUuid;
        } else if (uuid2.toString().equals(oldUuid)) {
            uuid2 = newUuid;
        } else if (uuid3.toString().equals(oldUuid)) {
            uuid3 = newUuid;
        } else if (uuid4.toString().equals(oldUuid)) {
            uuid4 = newUuid;
        } else {
            throw new CantFindOldUuidException("Can't find old UUID.");
        }
    }
}