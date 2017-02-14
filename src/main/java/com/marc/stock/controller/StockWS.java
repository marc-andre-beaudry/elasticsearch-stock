package com.marc.stock.controller;

import com.marc.stock.search.Stock;
import lombok.Data;

@Data
public class StockWS {

    private String symbol;
    private String name;

    public static StockWS fromStock(Stock stock) {
        StockWS stockWS = new StockWS();
        stockWS.setSymbol(stock.getSymbol());
        stockWS.setName(stock.getName());
        return stockWS;
    }
}
