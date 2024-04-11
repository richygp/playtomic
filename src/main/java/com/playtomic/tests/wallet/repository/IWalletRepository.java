package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IWalletRepository extends JpaRepository<Wallet, UUID> {
}
