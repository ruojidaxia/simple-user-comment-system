package com.fgj.comment.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ApiCategoryDto implements Serializable {
    private Integer id;

    @NotBlank(message = "name不能空白")
    @Size(message = "name长度不能大于8位",max = 8)
    private String name;

    private String description;
}
