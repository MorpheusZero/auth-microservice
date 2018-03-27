package net.snowlynxsoftware.authservice;

import net.snowlynxsoftware.authservice.services.HashService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WilyAuthServiceApplicationTests {

	/**
	 * Testing that hashing algorithms are working correctly.
	 */
	@Test
	public void hashTest() {

		String password = "myP@ssw0rd123-TEST";
		String hash;
		boolean isAuthentic;

		try {
			hash = HashService.getSaltedHash(password);
			isAuthentic = HashService.isAuthentic(password, hash);

		} catch(Exception ex) {
			isAuthentic = false;
		}

		Assert.assertTrue(isAuthentic);

	}

}
