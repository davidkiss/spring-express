package org.sbe.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sbe.sample.SampleApplication;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleApplication.class)
@WebAppConfiguration
public class SampleApplicationTests {

	@Test
	public void contextLoads() {
	}

}
