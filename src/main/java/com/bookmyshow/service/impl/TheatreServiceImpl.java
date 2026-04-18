package com.bookmyshow.service.impl;

import com.bookmyshow.dto.request.TheatreRequest;
import com.bookmyshow.dto.response.PagedResponse;
import com.bookmyshow.dto.response.TheatreResponse;
import com.bookmyshow.entity.Theatre;
import com.bookmyshow.exception.ResourceNotFoundException;
import com.bookmyshow.mapper.TheatreMapper;
import com.bookmyshow.repository.TheatreRepository;
import com.bookmyshow.service.TheatreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; @Service public class TheatreServiceImpl implements TheatreService {
    private final TheatreRepository theatreRepository;
    private final TheatreMapper theatreMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<TheatreResponse> getAllTheatres(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Theatre> theatres = theatreRepository.findAll(pageable);

        List<TheatreResponse> content = theatres.getContent().stream()
            .map(theatreMapper::toResponse)
            .collect(Collectors.toList());

        return PagedResponse.<TheatreResponse>builder()
            .content(content)
            .pageNumber(theatres.getNumber())
            .pageSize(theatres.getSize())
            .totalElements(theatres.getTotalElements())
            .totalPages(theatres.getTotalPages())
            .last(theatres.isLast())
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public TheatreResponse getTheatreById(Long theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId)
            .orElseThrow(() -> new ResourceNotFoundException("Theatre", "id", theatreId));
        return theatreMapper.toResponse(theatre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TheatreResponse> getTheatresByCity(String city) {
        List<Theatre> theatres = theatreRepository.findByCity(city);
        return theatres.stream()
            .map(theatreMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TheatreResponse createTheatre(TheatreRequest request) {
        Theatre theatre = theatreMapper.toEntity(request);
        theatre = theatreRepository.save(theatre);
        log.info("Theatre created successfully: {}", theatre.getId());
        return theatreMapper.toResponse(theatre);
    }

    @Override
    @Transactional
    public TheatreResponse updateTheatre(Long theatreId, TheatreRequest request) {
        Theatre theatre = theatreRepository.findById(theatreId)
            .orElseThrow(() -> new ResourceNotFoundException("Theatre", "id", theatreId));

        theatre.setName(request.getName());
        theatre.setAddress(request.getAddress());
        theatre.setCity(request.getCity());
        theatre.setState(request.getState());
        theatre.setPincode(request.getPincode());
        theatre = theatreRepository.save(theatre);
        log.info("Theatre updated successfully: {}", theatreId);
        return theatreMapper.toResponse(theatre);
    }

    @Override
    @Transactional
    public void deleteTheatre(Long theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId)
            .orElseThrow(() -> new ResourceNotFoundException("Theatre", "id", theatreId));
        theatreRepository.delete(theatre);
        log.info("Theatre deleted successfully: {}", theatreId);
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(TheatreServiceImpl.class);

    public TheatreServiceImpl(TheatreRepository theatreRepository, TheatreMapper theatreMapper) {
        this.theatreRepository = theatreRepository;
        this.theatreMapper = theatreMapper;
    }

}
