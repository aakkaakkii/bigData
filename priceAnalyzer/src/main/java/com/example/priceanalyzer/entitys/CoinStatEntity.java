package com.example.priceanalyzer.entitys;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class CoinStatEntity {
    @Id
    private String id;
    private double rsi;
    private String symbol;
    private List<Double> macd;
    private List<Double> macdSignal;
    private List<Double> ema10;
    private List<Double> ema40;
    private List<Double> date;
    private List<Double> price;

    public String getId() { return id; }
    public void setId( String id) { this.id = id; }
    public double getRsi() { return rsi; }
    public void setRsi( double rsi) { this.rsi = rsi; }
    public String getSymbol() { return symbol; }
    public void setSymbol( String symbol) { this.symbol = symbol; }
    public List<Double> getMacd() { return macd; }
    public void setMacd( List<Double> macd) { this.macd = macd; }
    public List<Double> getMacdSignal() { return macdSignal; }
    public void setMacdSignal( List<Double> macdSignal) { this.macdSignal = macdSignal; }
    public List<Double> getEma10() { return ema10; }
    public void setEma10( List<Double> ema10) { this.ema10 = ema10; }
    public List<Double> getEma40() { return ema40; }
    public void setEma40( List<Double> ema40) { this.ema40 = ema40; }
    public List<Double> getDate() { return date; }
    public void setDate( List<Double> date) { this.date = date; }
    public List<Double> getPrice() { return price; }
    public void setPrice(List<Double> price) { this.price = price; }
}
