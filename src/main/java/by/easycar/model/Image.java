package by.easycar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString

@Embeddable
public class Image {
    @Column(name = "im_filename")
    private String nameFile;
    @Column(name = "im_creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;
}
