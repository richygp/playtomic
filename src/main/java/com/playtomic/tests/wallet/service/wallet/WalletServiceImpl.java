package com.playtomic.tests.wallet.service.wallet;

import com.playtomic.tests.wallet.exception.IllegalTopUpArgument;
import com.playtomic.tests.wallet.exception.NoSuchWalletFound;
import com.playtomic.tests.wallet.model.TopUpPayment;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.ITopUpPaymentsRepository;
import com.playtomic.tests.wallet.repository.IWalletRepository;
import com.playtomic.tests.wallet.service.paymentplatform.IPaymentPlatformService;
import com.playtomic.tests.wallet.service.paymentplatform.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service("walletService")
public class WalletServiceImpl implements IWalletService {
    private final IWalletRepository walletRepository;
    private final ITopUpPaymentsRepository topUpPaymentsRepository;

    public WalletServiceImpl(IWalletRepository walletRepository, ITopUpPaymentsRepository topUpPaymentsRepository) {
        this.walletRepository = walletRepository;
        this.topUpPaymentsRepository = topUpPaymentsRepository;
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
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void topUpWallet(UUID walletId, IPaymentPlatformService paymentPlatformService, String creditCardNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalTopUpArgument();
        }
        Wallet wallet = getWalletById(walletId);
        Payment payment = paymentPlatformService.charge(creditCardNumber, amount);
        topUpPaymentsRepository.saveAndFlush(new TopUpPayment
                .TopUpPaymentBuilder(payment.id())
                .withWallet(wallet)
                .build());
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.saveAndFlush(wallet);
    }
}
