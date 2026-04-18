package com.bookmyshow.service.strategy;

import com.bookmyshow.enums.SeatType;
import org.springframework.stereotype.Component;

@Component
public class PricingStrategyFactory {

    public PricingStrategy getStrategy(SeatType seatType) {
        return switch (seatType) {
            case PREMIUM -> new PremiumPricingStrategy();
            case VIP -> new VipPricingStrategy();
            default -> new RegularPricingStrategy();
        };
    }
}
