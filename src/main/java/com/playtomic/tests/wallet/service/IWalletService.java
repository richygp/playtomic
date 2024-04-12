package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.Wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IWalletService {
    List<Wallet> getAllWallets();
    void createEmptyWallet();
    Wallet getWalletById(UUID walletId);
    BigDecimal getWalletBalance(UUID walletId);
    void topUpWallet(UUID walletId, String creditCardNumber, BigDecimal amount);
}
