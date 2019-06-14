package com.fgj.comment.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiCategoryVo implements Serializable {
    private Integer id;
    private String name;
    private String description;
}
