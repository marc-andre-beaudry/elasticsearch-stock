package com.marc.stock.search;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "stock", type = "stock", shards = 1, replicas = 0, refreshInterval = "-1")
@Getter
@Setter
public class Stock {

    @Id
    private String symbol;
    private String name;
}
