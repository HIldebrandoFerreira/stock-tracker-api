package com.brando.stocktracker.service;

import com.brando.stocktracker.client.response.BrapiStockDataResponse;
import com.brando.stocktracker.entity.Stock;
import com.brando.stocktracker.entity.StockPurchase;
import com.brando.stocktracker.repository.StockPurchaseRepository;
import com.brando.stocktracker.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final StockPurchaseRepository stockPurchaseRepository;
    private  final FindStockDetailService findStockDetailService;

    public Stock savePurchase(Stock stock, StockPurchase stockPurchase){
        StockPurchase savedPurchased = stockPurchaseRepository.save(stockPurchase);
        stock.setPurchases(List.of(savedPurchased));
        return stockRepository.save(stock);
    }

    public Stock addPurchase(String stockId, StockPurchase stockPurchase) {
        Optional<Stock> optStock =stockRepository.findById(stockId);

        return optStock.map(stock -> {
            StockPurchase savedStockPurchased = stockPurchaseRepository.save(stockPurchase);
            stock.getPurchases().add(savedStockPurchased);
            return stockRepository.save(stock);
        }).orElseThrow(() -> new IllegalArgumentException("ID: " + stockId + " Não encontrado"));
    }

    public List<Stock> findAll(){
        List<Stock> stocks = stockRepository.findAll();

        stocks.forEach(stock -> {
            Optional<BrapiStockDataResponse> brapiStockDetail = findStockDetailService.getBrapiStockDetail(stock.getStock());
            stock.setPrice(BigDecimal.valueOf(brapiStockDetail.map(BrapiStockDataResponse::getRegularMarketPrice).orElse(0.0)));
        });
        return stocks;
    }

}
