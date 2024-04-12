package com.playtomic.tests.wallet.dto;

import java.math.BigDecimal;

public record TopUpDTO(BigDecimal amount, String creditCardNumber) {
}
