package com.playtomic.tests.wallet.exception;

public record ResponseException(String timestamp, int status, String errorDescription, String path) {
}
