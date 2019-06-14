package com.fgj.comment.domain.dto;

import com.fgj.comment.pojo.DataType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ApiParameterDto {
    private Integer id;

    @NotNull
    private Integer apiId;

    @NotBlank(message = "name不能是空白值")
    @Size(max = 10,message = "name长度最大为10")
    private String name;

    @NotNull
    private DataType dataType;

    @NotNull
    private Boolean required;

    private String description;
}
