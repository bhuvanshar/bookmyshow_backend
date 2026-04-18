package com.bookmyshow.service;

import com.bookmyshow.dto.request.TheatreRequest;
import com.bookmyshow.dto.response.PagedResponse;
import com.bookmyshow.dto.response.TheatreResponse;

import java.util.List;

public interface TheatreService {
    PagedResponse<TheatreResponse> getAllTheatres(int page, int size);
    TheatreResponse getTheatreById(Long theatreId);
    List<TheatreResponse> getTheatresByCity(String city);
    TheatreResponse createTheatre(TheatreRequest request);
    TheatreResponse updateTheatre(Long theatreId, TheatreRequest request);
    void deleteTheatre(Long theatreId);
}
