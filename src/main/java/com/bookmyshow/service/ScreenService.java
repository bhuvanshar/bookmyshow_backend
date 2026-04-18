package com.bookmyshow.service;

import com.bookmyshow.dto.request.ScreenRequest;
import com.bookmyshow.dto.request.SeatBulkCreateRequest;
import com.bookmyshow.dto.response.ScreenResponse;

import java.util.List;

public interface ScreenService {
    List<ScreenResponse> getScreensByTheatre(Long theatreId);
    ScreenResponse getScreenById(Long screenId);
    ScreenResponse createScreen(ScreenRequest request);
    void addSeatsToScreen(Long screenId, SeatBulkCreateRequest request);
}
