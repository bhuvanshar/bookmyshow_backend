package com.bookmyshow.service.strategy;

import java.math.BigDecimal;

public class RegularPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice) {
        return basePrice;
    }
}
