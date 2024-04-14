package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.TopUpPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITopUpPaymentsRepository extends JpaRepository<TopUpPayment, UUID> {
}
