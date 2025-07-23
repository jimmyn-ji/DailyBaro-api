package com.project.model.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class QueryDiaryDTO {
    private Date dateFrom;
    private Date dateTo;
    private List<String> tags;
} 