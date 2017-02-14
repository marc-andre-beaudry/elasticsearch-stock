package com.marc.stock.controller;

import com.marc.stock.search.Stock;
import com.marc.stock.search.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/stockByName/{name}")
    public ResponseEntity<?> findStockByName(@PathVariable String name) {
        List<Stock> stockList = stockRepository.findByName(name);
        List<StockWS> stockWSList = stockList.stream().map(stock -> StockWS.fromStock(stock)).collect(Collectors.toList());
        return ResponseEntity.ok(stockWSList);
    }

    @GetMapping("/stockBySymbol/{symbol}")
    public ResponseEntity<?> findStockBySymbol(@PathVariable String symbol) {
        Stock stock = stockRepository.findOne(symbol);
        if (stock == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(StockWS.fromStock(stock));
    }
}
