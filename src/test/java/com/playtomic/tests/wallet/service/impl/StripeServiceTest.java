package com.playtomic.tests.wallet.service.impl;


import com.playtomic.tests.wallet.service.Payment;
import com.playtomic.tests.wallet.service.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.StripeServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;

import static org.mockito.ArgumentMatchers.any;

/**
 * This test is failing with the current implementation.
 *
 * How would you test this?
 * It is necessary to inject the restTemplate dependency.
 */
@ExtendWith(MockitoExtension.class)
class StripeServiceTest {

    @Mock
    private RestTemplate restTemplate;
    URI testUri = URI.create("http://how-would-you-test-me.localhost");
    @InjectMocks
    StripeService s = new StripeService(testUri, testUri, new RestTemplateBuilder());

    @Test
    void test_exception() {
        // Given
        Mockito
                .when(restTemplate.postForObject(any(URI.class),any(), any()))
                .thenThrow(StripeAmountTooSmallException.class);

        // When & Then
        Assertions.assertThrowsExactly(
                StripeAmountTooSmallException.class,
                () ->  s.charge("4242 4242 4242 4242", new BigDecimal(5)));

    }

    @Test
    void test_ok() throws StripeServiceException {
        // Given
        Payment payment = new Payment("1");
        Mockito
                .when(restTemplate.postForObject(any(URI.class),any(), any()))
                .thenReturn(payment);

        // When
        Payment paymentResponse = s.charge("4242 4242 4242 4242", new BigDecimal(15));

        // Then
        Assertions.assertNotNull(paymentResponse);
    }
}
