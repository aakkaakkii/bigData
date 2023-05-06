package com.example.priceanalyzer.entitys;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class IndicatorStatEntity {
    @Id
    private String id;
    private String symbol;
    private Double macd;
    private Double ema;
    private Double predictedPrice;
    private Double lastPrice;
    private Double nnSignal;
    private Double finalSignal;
    private double rsi;
    private double anomalySignal;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol;}
    public Double getMacd() { return macd;    }
    public void setMacd( Double macd) { this.macd = macd; }
    public Double getEma() { return ema;    }
    public void setEma( Double ema){ this.ema = ema;    }
    public double getRsi() { return rsi;    }
    public void setRsi( double rsi) { this.rsi = rsi; }
    public Double getPredictedPrice() { return predictedPrice; }
    public void setPredictedPrice( Double predictedPrice) { this.predictedPrice = predictedPrice; }
    public Double getLastPrice() { return lastPrice; }
    public void setLastPrice( Double lastPrice) { this.lastPrice = lastPrice; }
    public Double getNnSignal() { return nnSignal;    }
    public void setNnSignal( Double nnSignal) { this.nnSignal = nnSignal; }
    public Double getFinalSignal() { return finalSignal; }
    public void setFinalSignal( Double finalSignal) { this.finalSignal = finalSignal; }
    public double getAnomalySignal() { return anomalySignal; }
    public void setAnomalySignal( double anomalySignal) { this.anomalySignal = anomalySignal;}
}
