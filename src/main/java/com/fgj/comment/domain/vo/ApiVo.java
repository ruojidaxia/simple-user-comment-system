package com.fgj.comment.domain.vo;

import lombok.Data;
import org.springframework.http.HttpMethod;

import java.io.Serializable;

@Data
public class ApiVo implements Serializable {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String description;
    private String uri;
    private HttpMethod httpMethod;
}
