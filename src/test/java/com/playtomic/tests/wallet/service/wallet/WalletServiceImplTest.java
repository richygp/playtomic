package com.playtomic.tests.wallet.service.wallet;

import com.playtomic.tests.wallet.exception.IllegalTopUpArgument;
import com.playtomic.tests.wallet.exception.NoSuchWalletFound;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.IWalletRepository;
import com.playtomic.tests.wallet.service.paymentplatform.IPaymentPlatformService;
import com.playtomic.tests.wallet.service.paymentplatform.StripeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    @Mock
    private IWalletRepository walletRepository;
    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    void whenGetWalletByIdAndOkay() {
        // Given
        Wallet wallet = new Wallet();
        Optional<Wallet> optionalWallet = Optional.of(wallet);
        Mockito.when(walletRepository.findById(any())).thenReturn(optionalWallet);

        // When
        Wallet walletRequested = walletService.getWalletById(wallet.getUuid());

        // Then
        Assertions.assertNotNull(walletRequested);
        Assertions.assertEquals(wallet.getUuid(), walletRequested.getUuid());
    }

    @Test
    void whenGetWalletByIdAndNotSuchWalletFound() {
        // Given
        Wallet wallet = new Wallet();
        Mockito.when(walletRepository.findById(any())).thenThrow(NoSuchWalletFound.class);

        // When & Then
        Assertions.assertThrowsExactly(NoSuchWalletFound.class, () -> walletService.getWalletById(wallet.getUuid()));
    }

    @Test
    void whenGetWalletBalanceAndOkay() {
        // Given
        Wallet wallet = new Wallet();
        Optional<Wallet> optionalWallet = Optional.of(wallet);
        Mockito.when(walletRepository.findById(any())).thenReturn(optionalWallet);

        // When
        Wallet walletRequested = walletService.getWalletById(wallet.getUuid());

        // Then
        Assertions.assertNotNull(walletRequested);
        Assertions.assertEquals(wallet.getUuid(), walletRequested.getUuid());
        Assertions.assertEquals(wallet.getBalance(), walletRequested.getBalance());
    }

    @Test
    void whenTopUpWalletAndNegativeAmount() throws URISyntaxException {
        // Given
        Wallet wallet = new Wallet();
        IPaymentPlatformService paymentPlatformService = new StripeService(new URI("/void"), new URI("/void"), new RestTemplateBuilder());

        // When & Then
        Assertions.assertThrowsExactly(IllegalTopUpArgument.class, () -> walletService.topUpWallet(wallet.getUuid(), paymentPlatformService, "1111222233334444", BigDecimal.valueOf(-1L)));
    }
}