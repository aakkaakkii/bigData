package com.example.priceanalyzer.controller;

import com.example.priceanalyzer.entitys.CoinStatEntity;
import com.example.priceanalyzer.services.CoinStatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coin")
public class CoinStat {
    private final CoinStatService coinStatService;

    public CoinStat(CoinStatService coinStatService) {
        this.coinStatService = coinStatService;
    }

    @GetMapping("/{symbol}")
    public CoinStatEntity getBySymbol(@PathVariable String symbol) {
        return coinStatService.gedBySymbol(symbol);
    }
}
