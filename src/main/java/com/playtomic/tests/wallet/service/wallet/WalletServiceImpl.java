package com.playtomic.tests.wallet.service.wallet;

import com.playtomic.tests.wallet.exception.NoSuchWalletFound;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.IWalletRepository;
import com.playtomic.tests.wallet.service.IPaymentPlatformService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service("walletService")
public class WalletServiceImpl implements IWalletService {

    private final IWalletRepository walletRepository;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    private final Lock writeLock = rwLock.writeLock();

    public WalletServiceImpl(IWalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    @Override
    public Wallet createEmptyWallet() {
        return walletRepository.save(new Wallet());
    }

    @Override
    public Wallet getWalletById(UUID walletId) {
        return walletRepository.findById(walletId).orElseThrow(NoSuchWalletFound::new);
    }

    @Override
    public BigDecimal getWalletBalance(UUID walletId) {
        return getWalletById(walletId).getBalance();
    }

    @Override
    @Transactional
    public void topUpWallet(UUID walletId, IPaymentPlatformService paymentPlatformService, String creditCardNumber, BigDecimal amount) {
        Wallet wallet = getWalletById(walletId);
        paymentPlatformService.charge(creditCardNumber, amount);
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
    }
}
