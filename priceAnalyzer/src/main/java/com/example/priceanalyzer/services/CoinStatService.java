package com.example.priceanalyzer.services;

import com.example.priceanalyzer.entitys.CoinStatEntity;
import com.example.priceanalyzer.repo.CoinStatRepository;
import org.springframework.stereotype.Service;

@Service
public class CoinStatService {
    private final CoinStatRepository coinStatRepository;

    public CoinStatService(CoinStatRepository coinStatRepository) {
        this.coinStatRepository = coinStatRepository;
    }

    public CoinStatEntity gedBySymbol(String symbol) {
        return coinStatRepository.findBySymbol(symbol);
    }
}
