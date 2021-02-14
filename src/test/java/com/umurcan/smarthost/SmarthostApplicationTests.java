package com.umurcan.smarthost;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SmarthostApplicationTests {
	@Autowired
	private ApplicationContext appContext;

	@Test
	void contextLoads() {
		assertNotNull(appContext);
	}

}
