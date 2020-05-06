package com.friday.friday.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class BeanField implements Serializable {
    private static final long serialVersionUID = 7554516641213070834L;

    private String columnName;

    private String columnType;

    private String columnComment;

    private String columnDefault;

    private String name;

    private String type;
}
