package com.playtomic.tests.wallet.service.wallet;

import com.playtomic.tests.wallet.exception.NoSuchWalletFound;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.IWalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
}