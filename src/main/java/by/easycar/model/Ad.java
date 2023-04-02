package by.easycar.model;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class Ad {
    private Long id;
    private LocalDate creatingDate;
    private LocalDateTime upTime;
    private Integer viewsCount;
    private Integer price;
    private String VINNumber;
    private String description;
    private String region;
    private String phone;
    private Integer mileage;
    private String status;
    private Vehicle vehicle;
    private User user;
    private Set<File> images;
}
