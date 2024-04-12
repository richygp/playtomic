package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.exception.NoSuchWalletFound;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.IWalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service("walletService")
public class WalletServiceImpl implements IWalletService {

    private final IWalletRepository walletRepository;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public WalletServiceImpl(IWalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    @Override
    public void createEmptyWallet() {
        writeLock.lock();
        try {
            walletRepository.save(new Wallet());
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Wallet getWalletById(UUID walletId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (wallet.isEmpty()) {
            throw new NoSuchWalletFound();
        }
        return wallet.get();
    }

    @Override
    public BigDecimal getWalletBalance(UUID walletId) {
        readLock.lock();
        try {
            return getWalletById(walletId).getBalance();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void topUpWallet(UUID walletId, String creditCardNumber, BigDecimal amount) {
        writeLock.lock();
        try {
            Wallet wallet = getWalletById(walletId);
            wallet.setBalance(wallet.getBalance().add(amount));
            walletRepository.save(wallet);
        } finally {
            writeLock.unlock();
        }
    }
}
