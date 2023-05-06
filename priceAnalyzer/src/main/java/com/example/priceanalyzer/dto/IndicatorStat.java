package com.example.priceanalyzer.dto;

import com.example.priceanalyzer.entitys.IndicatorStatEntity;

public class IndicatorStat {
    public String symbol;
    public Double macd;
    public Double ema;
    public double rsi;
    public Double predictedPrice;
    public Double lastPrice;
    public Double signal;
    public Double anomalySignal;

    public String getSymbol() {
        return symbol;
    }

    public static IndicatorStatEntity toEntity(IndicatorStat model) {
        IndicatorStatEntity entity = new IndicatorStatEntity();
        entity.setSymbol(model.symbol);
        entity.setMacd(model.macd);
        entity.setEma(model.ema);
        entity.setRsi(model.rsi);
        entity.setLastPrice(model.lastPrice);
        entity.setPredictedPrice(model.predictedPrice);
        entity.setNnSignal(model.signal);
        entity.setAnomalySignal(model.anomalySignal);
        Double finalSignal = model.macd * 0.15 + model.ema * 0.15 + model.rsi * 0.15 + model.signal * 0.35 + model.anomalySignal * 0.2;
        entity.setFinalSignal(finalSignal);
        return entity;
    }
}
