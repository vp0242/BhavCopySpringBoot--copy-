package com.example.BhavCopySpringBoot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bhavcopy")
public class BhavcopyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name = "symbol")
    public String symbol;

    @Column(name = "series")
    public String series;

    @Column(name = "open")
    public Double open;

    @Column(name = "high")
    public Double high;

    @Column(name = "low")
    public Double low;

    @Column(name = "close")
    public Double close;

    @Column(name = "last")
    public Double last;

    @Column(name = "prevClose")
    public Double prevClose;

    @Column(name = "totalTradedQty")
    public Long totalTradedQty;

    @Column(name = "totalTradedVal")
    public Double totalTradedVal;

    @Column(name = "timestamp")
    public String timestamp;

    @Column(name = "totalTrades")
    public Integer totalTrades;

    @Column(name = "isin")
    public String isin;

    public BhavcopyModel() {

    }

    public BhavcopyModel(String symbol, String series, Double open, Double high, Double low, Double close, Double last,
            Double prevClose, Long totalTradedQty, Double totalTradedVal, String timestamp, Integer totalTrades,
            String isin) {
        this.symbol = symbol;
        this.series = series;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.last = last;
        this.prevClose = prevClose;
        this.totalTradedQty = totalTradedQty;
        this.totalTradedVal = totalTradedVal;
        this.timestamp = timestamp;
        this.totalTrades = totalTrades;
        this.isin = isin;
    }

}
