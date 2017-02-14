package com.marc.stock.controller

import com.marc.stock.search.Stock
import com.marc.stock.search.StockRepository
import org.hamcrest.Matchers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class SearchControllerSpec extends Specification {

    StockRepository stockRepository = Mock(StockRepository)

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new SearchController(stockRepository: stockRepository)).build()

    def "get stocks by name with empty repository"() {
        given: "an empty account collection"
        def stockCollection = Collections.emptyList()
        def queryString = "anyName"

        when: "Perform a search by name"
        def response = mockMvc.perform(get("/api/search/stockByName/" + queryString).contentType(APPLICATION_JSON))

        then: "the stockRepository is invoked and return the empty collection"
        1 * stockRepository.findByName(queryString) >> stockCollection

        and: "the http status is OK"
        response
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json("[]"))
    }

    def "get stocks by name with multiple matching results"() {
        given: "an empty account collection"
        def stock1 = new Stock(symbol: "AAPL", name: "Apple Inc.")
        def stock2 = new Stock(symbol: "APLE", name: "Apple Hospitality REIT, Inc.")
        def stockCollection = Arrays.asList(stock1, stock2)
        def queryString = "Apple"

        when: "Perform a search by name"
        def response = mockMvc.perform(get("/api/search/stockByName/" + queryString).contentType(APPLICATION_JSON))

        then: "the stockRepository is invoked and return the collection"
        1 * stockRepository.findByName(queryString) >> stockCollection

        and: "the http status is OK, array is size 2"
        response
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath('$', Matchers.hasSize(2)))
    }

    def "get stocks by symbol with unknown symbol"() {
        def queryString = "AAPL"

        when: "Perform a search by name"
        def response = mockMvc.perform(get("/api/search/stockBySymbol/" + queryString).contentType(APPLICATION_JSON))

        then: "the stockRepository is invoked and return null"
        1 * stockRepository.findOne(queryString) >> null

        and: "the http status is not found"
        response
                .andExpect(status().isNotFound())
    }

    def "get stocks by symbol with known symbol"() {
        def queryString = "AAPL"
        def stock = new Stock(symbol: "AAPL", name: "Apple Inc.")

        when: "Perform a search by name"
        def response = mockMvc.perform(get("/api/search/stockBySymbol/" + queryString).contentType(APPLICATION_JSON))

        then: "the stockRepository is invoked and return the stock"
        1 * stockRepository.findOne(queryString) >> stock

        and: "the http status is ok with proper symbol in the response"
        response
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath('$.symbol', Matchers.is(stock.symbol)))
    }
}