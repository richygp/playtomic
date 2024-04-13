package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.service.IPaymentPlatformService;
import com.playtomic.tests.wallet.service.wallet.IWalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class WalletControllerTest {

    @Mock
    private IWalletService walletService;
    @Mock
    private IPaymentPlatformService paymentPlatformService;
    @InjectMocks
    private WalletController walletController;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders
                .standaloneSetup(walletController)
                .build();
    }

    @Test
    void whenRequestingWalletBalanceAndOkay() throws Exception {
        // Given
        Wallet wallet = new Wallet();
        given(walletService.getWalletBalance(any())).willReturn(wallet.getBalance());

        // When
        MockHttpServletResponse response =
                mvc.perform(get("/api/v1/wallets/7fe42402-01f6-47dc-8eb0-7af26af6182e/balance"))
                        .andReturn()
                        .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("0", response.getContentAsString());
    }

    @Test
    void whenRequestingWalletTopUpAndOkay() throws Exception {
        // Given

        // When
        MockHttpServletResponse response =
                mvc.perform(
                        post("/api/v1/wallets/7fe42402-01f6-47dc-8eb0-7af26af6182e/balance/stripe")
                                .content("{\"amount\": \"2.232\", \"creditCardNumber\": \"1111222233334444\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}