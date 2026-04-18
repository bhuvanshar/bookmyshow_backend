package com.bookmyshow.mapper;

import com.bookmyshow.dto.request.ScreenRequest;
import com.bookmyshow.dto.response.ScreenResponse;
import com.bookmyshow.entity.Screen;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component public class ScreenMapper {

    public Screen toEntity(ScreenRequest request) {
        return Screen.builder()
                .name(request.getName())
                .totalSeats(request.getTotalSeats())
                .build();
    }

    public ScreenResponse toResponse(Screen screen) {
        if (screen == null) {
            return null;
        }

        String theatreName = null;
        Long theatreId = null;

        if (screen.getTheatre() != null) {
            theatreName = screen.getTheatre().getName();
            theatreId = screen.getTheatre().getId();
        }

        ScreenResponse response = ScreenResponse.builder()
                .id(screen.getId())
                .name(screen.getName())
                .theatreId(theatreId)
                .theatreName(theatreName)
                .totalSeats(screen.getTotalSeats())
                .build();

        return response;
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(ScreenMapper.class);

}
