package com.bookmyshow.mapper;

import com.bookmyshow.dto.request.TheatreRequest;
import com.bookmyshow.dto.response.TheatreResponse;
import com.bookmyshow.entity.Theatre;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component public class TheatreMapper {

    public Theatre toEntity(TheatreRequest request) {
        if (request == null) {
            return null;
        }

        Theatre theatre = Theatre.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .build();

        return theatre;
    }

    public TheatreResponse toResponse(Theatre theatre) {
        if (theatre == null) {
            return null;
        }

        int screenCount = 0;
        if (theatre.getScreens() != null) {
            screenCount = theatre.getScreens().size();
        }

        TheatreResponse response = TheatreResponse.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .address(theatre.getAddress())
                .city(theatre.getCity())
                .state(theatre.getState())
                .pincode(theatre.getPincode())
                .screenCount(screenCount)
                .build();

        return response;
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(TheatreMapper.class);

}
