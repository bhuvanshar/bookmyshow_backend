package com.bookmyshow.mapper;

import com.bookmyshow.dto.request.ShowRequest;
import com.bookmyshow.dto.response.ShowResponse;
import com.bookmyshow.entity.Show;
import com.bookmyshow.enums.SeatStatus;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component public class ShowMapper {

    public Show toEntity(ShowRequest request) {
        return Show.builder()
                .showDate(request.getShowDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .basePrice(request.getBasePrice())
                .build();
    }

    public ShowResponse toResponse(Show show) {
        if (show == null) {
            return null;
        }

        String movieTitle = null;
        Long movieId = null;
        if (show.getMovie() != null) {
            movieTitle = show.getMovie().getTitle();
            movieId = show.getMovie().getId();
        }

        String theatreName = null;
        Long theatreId = null;
        if (show.getScreen() != null && show.getScreen().getTheatre() != null) {
            theatreName = show.getScreen().getTheatre().getName();
            theatreId = show.getScreen().getTheatre().getId();
        }

        String screenName = null;
        Long screenId = null;
        if (show.getScreen() != null) {
            screenName = show.getScreen().getName();
            screenId = show.getScreen().getId();
        }

        int availableSeats = 0;
        if (show.getShowSeats() != null) {
            availableSeats = (int) show.getShowSeats().stream()
                    .filter(seat -> SeatStatus.AVAILABLE == seat.getStatus())
                    .count();
        }

        ShowResponse response = ShowResponse.builder()
                .id(show.getId())
                .movieId(movieId)
                .movieTitle(movieTitle)
                .theatreId(theatreId)
                .theatreName(theatreName)
                .screenId(screenId)
                .screenName(screenName)
                .showDate(show.getShowDate())
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .basePrice(show.getBasePrice())
                .status(show.getStatus() != null ? show.getStatus().name() : null)
                .availableSeats(availableSeats)
                .build();

        return response;
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(ShowMapper.class);

}
