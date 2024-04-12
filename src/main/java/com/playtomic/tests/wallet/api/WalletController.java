package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.dto.TopUpDTO;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.service.IPaymentPlatformService;
import com.playtomic.tests.wallet.service.IWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
public class WalletController {
    private final Logger log = LoggerFactory.getLogger(WalletController.class);
    private final IWalletService walletService;
    private final IPaymentPlatformService paymentPlatformService;

    public WalletController(IWalletService walletService, IPaymentPlatformService paymentPlatformService) {
        this.walletService = walletService;
        this.paymentPlatformService = paymentPlatformService;
    }

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }

    @RequestMapping("/wallets")
    List<Wallet> getAllWallets() {
        log.info("Returning all wallets");
        return walletService.getAllWallets();
    }

    @RequestMapping("/wallets/{uuid}")
    Wallet getWalletById(@PathVariable UUID uuid) {
        log.info("Returning a specific wallet by UUID");
        return walletService.getWalletById(uuid);
    }

    @PostMapping("/wallets")
    void registerWallet() {
        log.info("Registering a new wallet");
        walletService.createEmptyWallet();
    }

    @RequestMapping("/wallets/{uuid}/balance")
    BigDecimal getWalletBalance(@PathVariable UUID uuid) {
        log.info("Returning a specific wallet balance");
        return walletService.getWalletBalance(uuid);
    }

    @PostMapping("/wallets/{uuid}/balance")
    void topUpWalletBalance(@PathVariable UUID uuid, @RequestBody TopUpDTO topUpDTO) {
        log.info("Returning a specific wallet balance");
        walletService.topUpWallet(uuid, paymentPlatformService, topUpDTO.creditCardNumber(), topUpDTO.amount());
    }
}
