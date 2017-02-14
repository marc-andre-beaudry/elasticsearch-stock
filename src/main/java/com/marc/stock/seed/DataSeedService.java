package com.marc.stock.seed;

import com.marc.stock.Application;
import com.marc.stock.search.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataSeedService {

    public List<Stock> getSeedStock() {
        List<Stock> stocks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Application.class.getResourceAsStream("/data/company_seed.data")))) {
            stocks = br.lines().skip(1).map((line) -> {
                Stock stock = new Stock();
                String[] splittedLine = line.split("\\|");
                stock.setSymbol(splittedLine[0]);
                stock.setName(splittedLine[1]);
                return stock;
            }).filter(x -> x != null)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return stocks;
    }
}
