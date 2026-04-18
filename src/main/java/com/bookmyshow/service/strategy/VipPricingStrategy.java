package com.bookmyshow.service.strategy;

import java.math.BigDecimal;

public class VipPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice) {
        return basePrice.multiply(BigDecimal.valueOf(2.0));
    }
}
