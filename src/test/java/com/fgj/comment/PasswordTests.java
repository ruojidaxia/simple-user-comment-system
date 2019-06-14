package com.fgj.comment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PasswordTests {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void test(){
        System.out.println(passwordEncoder.encode("123456"));
    }
}
