package com.example.priceanalyzer.controller;

import com.example.priceanalyzer.entitys.IndicatorStatEntity;
import com.example.priceanalyzer.services.IndicatorStatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/indicator")
public class IndicatorStat {
    private final IndicatorStatService indicatorStatService;

    public IndicatorStat(IndicatorStatService indicatorStatService) {
        this.indicatorStatService = indicatorStatService;
    }

    @GetMapping
    public List<IndicatorStatEntity> loadAll() {
        return indicatorStatService.loadAll();
    }

    @GetMapping("/{symbol}")
    public IndicatorStatEntity getBySymbol(@PathVariable String symbol) {
        return indicatorStatService.gedBySymbol(symbol);
    }
}
