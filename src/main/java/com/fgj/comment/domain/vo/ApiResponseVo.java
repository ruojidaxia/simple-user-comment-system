package com.fgj.comment.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResponseVo implements Serializable {
    private Integer id;
    private Integer apiId;
    private Integer status;
    private String description;
    private String result;
}
