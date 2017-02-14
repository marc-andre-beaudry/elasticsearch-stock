package com.marc.stock;

import com.marc.stock.search.Stock;
import com.marc.stock.search.StockRepository;
import com.marc.stock.seed.DataSeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private DataSeedService dataSeedService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Stock> stocks = dataSeedService.getSeedStock();
        stockRepository.save(stocks);
    }
}