package com.shubnikofff.testtransactional.service;

import com.shubnikofff.testtransactional.dto.LikeRequest;
import com.shubnikofff.testtransactional.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void addToHistory(LikeRequest request, String status) {
        try {
            historyRepository.save(request, status);
        } catch (Exception e) {
            log.error("Unable to save history", e);
        }
    }

}
