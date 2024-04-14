package com.playtomic.tests.wallet.service.paymentplatform;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

public record Payment(@NonNull String id) {

    @JsonCreator
    public Payment(@JsonProperty(value = "id", required = true) String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }
}
