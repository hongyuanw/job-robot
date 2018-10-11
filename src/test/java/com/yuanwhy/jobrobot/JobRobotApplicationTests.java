package com.yuanwhy.jobrobot;

import com.yuanwhy.jobrobot.crawler.JobReader;
import com.yuanwhy.jobrobot.crawler.impl.HttpJobReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobRobotApplicationTests {

	@Autowired
	private JobReader jobReader;

	@Before
	public void contextLoads() {
	}

	@Test
	public void testJobReader() {
		System.out.println(jobReader.read(HttpJobReader.ALIBAB_JOB_URL));
	}

}
