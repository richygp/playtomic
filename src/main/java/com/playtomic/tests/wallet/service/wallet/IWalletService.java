package com.playtomic.tests.wallet.service.wallet;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.service.paymentplatform.IPaymentPlatformService;

import java.math.BigDecimal;
import java.util.UUID;

public interface IWalletService {
    Wallet createEmptyWallet();
    Wallet getWalletById(UUID walletId);
    BigDecimal getWalletBalance(UUID walletId);
    void topUpWallet(UUID walletId, IPaymentPlatformService paymentPlatformService, String creditCardNumber, BigDecimal amount);
}
