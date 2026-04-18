package com.bookmyshow.service.strategy;

import java.math.BigDecimal;

public class PremiumPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice) {
        return basePrice.multiply(BigDecimal.valueOf(1.5));
    }
}
