package com.bookmyshow.service.impl;

import com.bookmyshow.dto.request.ScreenRequest;
import com.bookmyshow.dto.request.SeatBulkCreateRequest;
import com.bookmyshow.dto.response.ScreenResponse;
import com.bookmyshow.entity.Screen;
import com.bookmyshow.entity.Seat;
import com.bookmyshow.entity.Theatre;
import com.bookmyshow.exception.ResourceNotFoundException;
import com.bookmyshow.mapper.ScreenMapper;
import com.bookmyshow.repository.ScreenRepository;
import com.bookmyshow.repository.SeatRepository;
import com.bookmyshow.repository.TheatreRepository;
import com.bookmyshow.service.ScreenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @Service public class ScreenServiceImpl implements ScreenService {
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenMapper screenMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ScreenResponse> getScreensByTheatre(Long theatreId) {
        theatreRepository.findById(theatreId)
            .orElseThrow(() -> new ResourceNotFoundException("Theatre", "id", theatreId));

        List<Screen> screens = screenRepository.findByTheatreId(theatreId);
        return screens.stream()
            .map(screenMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ScreenResponse getScreenById(Long screenId) {
        Screen screen = screenRepository.findById(screenId)
            .orElseThrow(() -> new ResourceNotFoundException("Screen", "id", screenId));
        return screenMapper.toResponse(screen);
    }

    @Override
    @Transactional
    public ScreenResponse createScreen(ScreenRequest request) {
        Theatre theatre = theatreRepository.findById(request.getTheatreId())
            .orElseThrow(() -> new ResourceNotFoundException("Theatre", "id", request.getTheatreId()));

        Screen screen = screenMapper.toEntity(request);
        screen.setTheatre(theatre);
        screen = screenRepository.save(screen);
        log.info("Screen created successfully: {}", screen.getId());
        return screenMapper.toResponse(screen);
    }

    @Override
    @Transactional
    public void addSeatsToScreen(Long screenId, SeatBulkCreateRequest request) {
        Screen screen = screenRepository.findById(screenId)
            .orElseThrow(() -> new ResourceNotFoundException("Screen", "id", screenId));

        List<Seat> seatsToCreate = new ArrayList<>();

        for (SeatBulkCreateRequest.RowConfig rowConfig : request.getRows()) {
            for (int i = 1; i <= rowConfig.getSeatCount(); i++) {
                Seat seat = Seat.builder()
                    .screen(screen)
                    .rowName(rowConfig.getRowName())
                    .seatNumber(i)
                    .seatType(rowConfig.getSeatType())
                    .build();
                seatsToCreate.add(seat);
            }
        }

        seatRepository.saveAll(seatsToCreate);
        log.info("Added {} seats to screen: {}", seatsToCreate.size(), screenId);
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(ScreenServiceImpl.class);

    public ScreenServiceImpl(ScreenRepository screenRepository, SeatRepository seatRepository, TheatreRepository theatreRepository, ScreenMapper screenMapper) {
        this.screenRepository = screenRepository;
        this.seatRepository = seatRepository;
        this.theatreRepository = theatreRepository;
        this.screenMapper = screenMapper;
    }

}
