package by.easycar.model.requests;

import lombok.Data;

@Data
public class SearchParams {
    private String entity;
    private String key;
    private String operation;
    private String value;
}