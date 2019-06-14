package com.fgj.comment.domain.dto;

import lombok.Data;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class ApiDto{
    private Integer id;

    @Positive
    private Integer categoryId;

    @NotBlank
    @Size(max = 10)
    private String name;

    private String description;

    @NotBlank
    private String uri;

    @NotNull
    private HttpMethod httpMethod;
}
