package com.playtomic.tests.wallet;

import com.playtomic.tests.wallet.api.WalletController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class WalletApplicationIT {

	@Autowired
	private WalletController walletController;

	@Test
	void contextLoads() throws Exception {
		Assertions.assertNotNull(walletController);
	}
}
