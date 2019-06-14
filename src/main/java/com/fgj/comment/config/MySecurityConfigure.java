package com.fgj.comment.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class MySecurityConfigure extends AbstractSecurityWebApplicationInitializer {
    public void go(){
        SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

}
