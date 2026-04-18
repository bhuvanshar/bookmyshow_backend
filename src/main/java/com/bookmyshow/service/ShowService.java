package com.bookmyshow.service;

import com.bookmyshow.dto.request.ShowRequest;
import com.bookmyshow.dto.response.PagedResponse;
import com.bookmyshow.dto.response.ShowResponse;
import com.bookmyshow.dto.response.ShowSeatResponse;

import java.util.List;

public interface ShowService {
    List<ShowResponse> getShowsByMovie(Long movieId);
    ShowResponse getShowById(Long showId);
    List<ShowResponse> getShowsByMovieAndCity(Long movieId, String city);
    ShowResponse createShow(ShowRequest request);
    void cancelShow(Long showId);
    List<ShowSeatResponse> getShowSeats(Long showId);
}
