package com.example.priceanalyzer.services;

import com.example.priceanalyzer.entitys.IndicatorStatEntity;
import com.example.priceanalyzer.repo.IndicatorStatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndicatorStatService {
    private final IndicatorStatRepository indicatorStatRepository;

    public IndicatorStatService(IndicatorStatRepository indicatorStatRepository) {
        this.indicatorStatRepository = indicatorStatRepository;
    }

    public IndicatorStatEntity gedBySymbol(String symbol) {
        return indicatorStatRepository.findBySymbol(symbol);
    }

    public List<IndicatorStatEntity> loadAll() {
        return indicatorStatRepository.findAll();
    }
}
