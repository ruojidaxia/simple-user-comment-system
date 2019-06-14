package com.fgj.comment.domain.vo;

import com.fgj.comment.pojo.DataType;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApiParameterVo implements Serializable {
    private Integer id;
    private Integer apiId;
    private String name;
    private DataType dataType;
    private Boolean required;
    private String description;
}
