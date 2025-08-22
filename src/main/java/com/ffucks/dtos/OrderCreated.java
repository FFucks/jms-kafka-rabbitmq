package com.ffucks.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OrderCreated(
        @NotBlank String orderId,
        @NotBlank String customer,
        @Positive double total
) {}
