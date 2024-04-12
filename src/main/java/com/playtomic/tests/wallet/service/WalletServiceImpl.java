package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.exception.NoSuchWalletFound;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.IWalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("walletService")
public class WalletServiceImpl implements IWalletService {

    private final IWalletRepository walletRepository;

    public WalletServiceImpl(IWalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    @Override
    public void createEmptyWallet() {
        walletRepository.save(new Wallet());
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
        // TODO: Also think it could be a running top-up transaction on this walletId
        return getWalletById(walletId).getBalance();
    }

    @Override
    public void topUpWallet(UUID walletId, String creditCardNumber, BigDecimal amount) {
        // TODO: Need to be thread safe ;)
        Wallet wallet = getWalletById(walletId);
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
    }
}
