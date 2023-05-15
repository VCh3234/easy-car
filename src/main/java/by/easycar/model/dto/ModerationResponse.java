package by.easycar.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ModerationResponse {

    private Long id;

    private LocalDateTime creationDate;

    private String message;

    private Long adminId;

    private Long advertisementId;
}