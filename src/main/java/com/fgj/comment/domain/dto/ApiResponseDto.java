package com.fgj.comment.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class ApiResponseDto{
    private Integer id;

    @Positive
    private Integer apiId;

    @Positive
    private Integer status;

    private String description;

    @NotBlank
    private String result;
}
