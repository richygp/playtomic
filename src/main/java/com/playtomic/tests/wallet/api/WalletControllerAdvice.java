package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.exception.NoSuchWalletFound;
import com.playtomic.tests.wallet.exception.ResponseException;
import com.playtomic.tests.wallet.service.StripeAmountTooSmallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class WalletControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(WalletControllerAdvice.class);

    @ExceptionHandler(NoSuchWalletFound.class)
    public ResponseEntity<Object> handleNoSuchWalletFoundException(WebRequest request) {
        log.warn("Failed to find the requested Wallet");
        String path = ((ServletWebRequest) request).getRequest().getServletPath();
        return buildResponse(HttpStatus.NOT_FOUND, "Wallet Not Found", path);

    }

    @ExceptionHandler(StripeAmountTooSmallException.class)
    public ResponseEntity<Object> handleStripeAmountTooSmallException(WebRequest request) {
        log.warn("Failed to perform stripe payment");
        String path = ((ServletWebRequest) request).getRequest().getServletPath();
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Stripe amount too small", path);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtException(WebRequest request) {
        log.error("Unknown error occurred");
        String path = ((ServletWebRequest) request).getRequest().getServletPath();
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error", path);
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message, String path) {
        ResponseException errorResponse = new ResponseException(
                LocalDateTime.now().toString(), status.value(), message, path);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
