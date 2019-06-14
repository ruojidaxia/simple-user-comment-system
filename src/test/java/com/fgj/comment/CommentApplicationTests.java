package com.fgj.comment;

import com.fgj.comment.config.properties.SystemProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentApplicationTests {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	@Autowired
	private SystemProperties systemProperties;

	@Before
	public void before(){
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	@Test
	public void test() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET,"/api-category")
				.accept(MediaType.APPLICATION_JSON_UTF8);
		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	@Test
	public void test2(){
		System.out.println(111);
	}

}
