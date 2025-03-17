package com.project.common;

public class StockData {
    private String stockName;
    private double stockPrice;

    public StockData(String stockName, double stockPrice) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
    }

    public String getStockName() {
        return stockName;
    }

    public double getStockPrice() {
        return stockPrice;
    }
}
