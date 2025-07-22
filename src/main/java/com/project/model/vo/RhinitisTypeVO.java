package com.project.model.vo;

import lombok.Data;

@Data
public class RhinitisTypeVO {
    private Long rid;
    private String rhinitisType;
    private String symptom;
    private String cause;
    private String treatmentMethod;
    private String notice;
    private String imageGuid;
}