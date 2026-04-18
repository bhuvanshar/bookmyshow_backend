package com.bookmyshow.service.impl;

import com.bookmyshow.dto.request.ShowRequest;
import com.bookmyshow.dto.response.ShowResponse;
import com.bookmyshow.dto.response.ShowSeatResponse;
import com.bookmyshow.entity.Movie;
import com.bookmyshow.entity.Screen;
import com.bookmyshow.entity.Seat;
import com.bookmyshow.entity.Show;
import com.bookmyshow.entity.ShowSeat;
import com.bookmyshow.enums.SeatStatus;
import com.bookmyshow.enums.ShowStatus;
import com.bookmyshow.exception.ResourceNotFoundException;
import com.bookmyshow.mapper.ShowMapper;
import com.bookmyshow.mapper.ShowSeatMapper;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.repository.ScreenRepository;
import com.bookmyshow.repository.SeatRepository;
import com.bookmyshow.repository.ShowRepository;
import com.bookmyshow.repository.ShowSeatRepository;
import com.bookmyshow.service.ShowService;
import com.bookmyshow.service.strategy.PricingStrategy;
import com.bookmyshow.service.strategy.PricingStrategyFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @Service public class ShowServiceImpl implements ShowService {
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final ShowMapper showMapper;
    private final ShowSeatMapper showSeatMapper;
    private final PricingStrategyFactory pricingStrategyFactory;

    @Override
    @Transactional(readOnly = true)
    public List<ShowResponse> getShowsByMovie(Long movieId) {
        movieRepository.findById(movieId)
            .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));

        List<Show> shows = showRepository.findByMovieIdAndStatus(movieId, ShowStatus.SCHEDULED);
        return shows.stream()
            .map(showMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ShowResponse getShowById(Long showId) {
        Show show = showRepository.findById(showId)
            .orElseThrow(() -> new ResourceNotFoundException("Show", "id", showId));
        return showMapper.toResponse(show);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowResponse> getShowsByMovieAndCity(Long movieId, String city) {
        movieRepository.findById(movieId)
            .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));

        List<Show> shows = showRepository.findByMovieIdAndScreenTheatreCityAndStatus(movieId, city, ShowStatus.SCHEDULED);
        return shows.stream()
            .map(showMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShowResponse createShow(ShowRequest request) {
        Movie movie = movieRepository.findById(request.getMovieId())
            .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", request.getMovieId()));

        Screen screen = screenRepository.findById(request.getScreenId())
            .orElseThrow(() -> new ResourceNotFoundException("Screen", "id", request.getScreenId()));

        Show show = showMapper.toEntity(request);
        show.setMovie(movie);
        show.setScreen(screen);
        show.setStatus(ShowStatus.SCHEDULED);
        show = showRepository.save(show);

        List<Seat> seats = seatRepository.findByScreenId(screen.getId());
        List<ShowSeat> showSeats = new ArrayList<>();

        for (Seat seat : seats) {
            PricingStrategy strategy = pricingStrategyFactory.getStrategy(seat.getSeatType());
            ShowSeat showSeat = ShowSeat.builder()
                .show(show)
                .seat(seat)
                .status(SeatStatus.AVAILABLE)
                .price(strategy.calculatePrice(show.getBasePrice()))
                .build();
            showSeats.add(showSeat);
        }

        showSeatRepository.saveAll(showSeats);
        log.info("Show created successfully with {} show seats: {}", showSeats.size(), show.getId());
        return showMapper.toResponse(show);
    }

    @Override
    @Transactional
    public void cancelShow(Long showId) {
        Show show = showRepository.findById(showId)
            .orElseThrow(() -> new ResourceNotFoundException("Show", "id", showId));

        show.setStatus(ShowStatus.CANCELLED);
        showRepository.save(show);

        List<ShowSeat> showSeats = showSeatRepository.findByShowId(showId);
        for (ShowSeat seat : showSeats) {
            if (seat.getStatus() == SeatStatus.AVAILABLE || seat.getStatus() == SeatStatus.LOCKED) {
                seat.setStatus(SeatStatus.UNAVAILABLE);
            }
        }
        showSeatRepository.saveAll(showSeats);
        log.info("Show cancelled successfully: {}", showId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowSeatResponse> getShowSeats(Long showId) {
        Show show = showRepository.findById(showId)
            .orElseThrow(() -> new ResourceNotFoundException("Show", "id", showId));

        List<ShowSeat> showSeats = showSeatRepository.findByShowId(showId);
        return showSeats.stream()
            .map(showSeatMapper::toResponse)
            .collect(Collectors.toList());
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(ShowServiceImpl.class);

    public ShowServiceImpl(ShowRepository showRepository, ShowSeatRepository showSeatRepository, MovieRepository movieRepository, ScreenRepository screenRepository, SeatRepository seatRepository, ShowMapper showMapper, ShowSeatMapper showSeatMapper, PricingStrategyFactory pricingStrategyFactory) {
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.seatRepository = seatRepository;
        this.showMapper = showMapper;
        this.showSeatMapper = showSeatMapper;
        this.pricingStrategyFactory = pricingStrategyFactory;
    }

}
