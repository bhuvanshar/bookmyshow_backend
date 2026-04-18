package com.bookmyshow.mapper;

import com.bookmyshow.dto.response.ShowSeatResponse;
import com.bookmyshow.entity.ShowSeat;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component public class ShowSeatMapper {

    public ShowSeatResponse toResponse(ShowSeat showSeat) {
        if (showSeat == null) {
            return null;
        }

        String rowName = null;
        Integer seatNumber = null;
        String seatType = null;

        if (showSeat.getSeat() != null) {
            rowName = showSeat.getSeat().getRowName();
            seatNumber = showSeat.getSeat().getSeatNumber();
            if (showSeat.getSeat().getSeatType() != null) {
                seatType = showSeat.getSeat().getSeatType().name();
            }
        }

        ShowSeatResponse response = ShowSeatResponse.builder()
                .id(showSeat.getId())
                .rowName(rowName)
                .seatNumber(seatNumber)
                .seatType(seatType)
                .status(showSeat.getStatus() != null ? showSeat.getStatus().name() : null)
                .price(showSeat.getPrice())
                .build();

        return response;
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(ShowSeatMapper.class);

}
