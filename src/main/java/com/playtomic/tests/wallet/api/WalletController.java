package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.IWalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class WalletController {
    private Logger log = LoggerFactory.getLogger(WalletController.class);
    private final IWalletRepository walletRepository;

    public WalletController(IWalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }

    @RequestMapping("/wallets")
    List<Wallet> getAllWallets() {
        log.info("Returning all wallets");
        return walletRepository.findAll();
    }

    @RequestMapping("/wallets/{uuid}")
    Optional<Wallet> getWalletById(@PathVariable UUID uuid) {
        log.info("Returning a specific wallet by UUID");
        return walletRepository.findById(uuid);
    }

    @PostMapping("/wallets")
    void registerWallet() {
        log.info("Registering a new wallet");
        walletRepository.save(new Wallet());
    }
}
