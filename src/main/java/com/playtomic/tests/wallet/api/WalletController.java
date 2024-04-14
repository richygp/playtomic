package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.dto.TopUpDTO;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.service.paymentplatform.IPaymentPlatformService;
import com.playtomic.tests.wallet.service.wallet.IWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {
    private final Logger log = LoggerFactory.getLogger(WalletController.class);
    private final IWalletService walletService;
    private final IPaymentPlatformService stripePaymentPlatformService;

    public WalletController(IWalletService walletService, IPaymentPlatformService stripePaymentPlatformService) {
        this.walletService = walletService;
        this.stripePaymentPlatformService = stripePaymentPlatformService;
    }

    @PostMapping("/")
    ResponseEntity<Wallet> registerWallet() {
        Wallet wallet = walletService.createEmptyWallet();
        log.info("New empty wallet created with uuid: {}", wallet.getUuid());
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path(wallet.getUuid().toString())
                        .buildAndExpand(wallet).toUri())
                .build();
    }

    @RequestMapping("/{uuid}/balance")
    BigDecimal getWalletBalance(@PathVariable UUID uuid) {
        log.info("Returning a specific wallet balance");
        return walletService.getWalletBalance(uuid);
    }

    @PostMapping("/{uuid}/balance/top-up-stripe")
    void topUpWalletBalanceWithStripe(@PathVariable UUID uuid, @RequestBody TopUpDTO topUpDTO) {
        log.info("Topping up Wallet id {} using Stripe strategy", uuid);
        walletService.topUpWallet(uuid, stripePaymentPlatformService, topUpDTO.creditCardNumber(), topUpDTO.amount());
    }

    @PostMapping("/{uuid}/balance/top-up-paypal")
    ResponseEntity<Object> topUpWalletBalanceWithPaypal(@PathVariable UUID uuid) {
        log.info("Topping up Wallet id {} using Paypal strategy", uuid);
        log.warn("Yet not implemented strategy for Paypal");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
