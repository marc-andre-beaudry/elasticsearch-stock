package com.marc.stock.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface StockRepository extends ElasticsearchRepository<Stock, String> {
    List<Stock> findByName(String name);
}
