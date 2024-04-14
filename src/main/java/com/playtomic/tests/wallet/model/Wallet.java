package com.playtomic.tests.wallet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This entity encapsulates the Wallet features in our exercise.
 * It could be interesting to link a Wallet with a specific User, but
 * could be the remote scenario of "anonymous wallets" ?
 *
 * It was considered to keep the topUp history by linking this
 * with {@link TopUpPayment} which stores the {@link com.playtomic.tests.wallet.service.paymentplatform.Payment} info.
 */
@Entity
public class Wallet {
    @Id
    @GeneratedValue
    private UUID uuid;
    private BigDecimal balance;
    @OneToMany(mappedBy = "wallet")
    private List<TopUpPayment> topUpPayments;

    public Wallet() {
        this.balance = BigDecimal.ZERO;
        this.topUpPayments = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<TopUpPayment> getTopUpPayments() {
        return topUpPayments;
    }

    public void setTopUpPayments(List<TopUpPayment> topUpPayments) {
        this.topUpPayments = topUpPayments;
    }
}
