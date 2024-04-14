package com.playtomic.tests.wallet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class TopUpPayment {
    @Id
    @GeneratedValue
    private UUID uuid;
    private String paymentId;
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    private TopUpPayment(TopUpPaymentBuilder topUpPaymentBuilder) {
        this.paymentId = topUpPaymentBuilder.paymentId;
        this.wallet = topUpPaymentBuilder.wallet;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public static class TopUpPaymentBuilder {
        private final String paymentId;
        private Wallet wallet;

        public TopUpPaymentBuilder(String paymentId) {
            this.paymentId = paymentId;
        }

        public TopUpPaymentBuilder withWallet(Wallet wallet) {
            this.wallet = wallet;
            return this;
        }

        public TopUpPayment build() {
            return new TopUpPayment(this);
        }
    }
}
