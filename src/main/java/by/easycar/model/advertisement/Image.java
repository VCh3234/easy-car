package by.easycar.model.advertisement;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString

@Embeddable
public class Image {

    @Column(name = "im_filename", unique = true, updatable = false)
    private UUID nameFile; //TODO
    @Column(name = "im_creation_date")
    private LocalDateTime creationDate = LocalDateTime.now(); //TODO: test
}
