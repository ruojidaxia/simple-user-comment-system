package com.fgj.comment;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class MybatisTest {
    @Test
    public void test() throws FileNotFoundException {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(new FileReader(""));
        SqlSession session = factory.openSession();
        session.close();
    }
}
