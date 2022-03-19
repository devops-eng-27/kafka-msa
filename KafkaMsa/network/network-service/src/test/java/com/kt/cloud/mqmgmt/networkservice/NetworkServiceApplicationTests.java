package com.kt.cloud.mqmgmt.networkservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = NetworkServiceApplication.class)
class NetworkServiceApplicationTests {

//	@Autowired
//	private MockMvc mvc;
//
//	@Test
//	public void contextLoads() throws Exception {
//		mvc.perform(get("/net/setip"))
//				.andExpect(status().isOk());
//
//	}


}
