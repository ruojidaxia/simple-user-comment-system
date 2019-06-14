package com.fgj.comment.domain.qo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ApiCategoryQo implements Serializable {
    @NotBlank(message = "name不能空白")
    @Size(message = "name长度不能大于8位",max = 8)
    private String name;
}
