package com.example.priceanalyzer.dto;

import com.example.priceanalyzer.entitys.CoinStatEntity;

import java.util.List;

public class CoinStat {
    public String symbol;
    public List<Double> macd;
    public List<Double> macdSignal;
    public List<Double> ema10;
    public List<Double> ema40;
    public List<Double> date;
    public List<Double> price;
    public double rsi;

    public static CoinStatEntity toEntity(CoinStat coinStat) {
        CoinStatEntity coinStatEntity = new CoinStatEntity();
        coinStatEntity.setSymbol(coinStat.symbol);
        coinStatEntity.setMacd(coinStat.macd);
        coinStatEntity.setMacdSignal(coinStat.macdSignal);
        coinStatEntity.setEma10(coinStat.ema10);
        coinStatEntity.setEma40(coinStat.ema40);
        coinStatEntity.setPrice(coinStat.price);
        coinStatEntity.setDate(coinStat.date);
        coinStatEntity.setRsi(coinStat.rsi);
        return coinStatEntity;
    }
}
