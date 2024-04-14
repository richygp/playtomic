package com.playtomic.tests.wallet.service.paymentplatform;

import java.math.BigDecimal;

public interface IPaymentPlatformService {
    Payment charge(String creditCardNumber, BigDecimal amount) throws StripeServiceException;
    void refund(String paymentId) throws StripeServiceException;
}
