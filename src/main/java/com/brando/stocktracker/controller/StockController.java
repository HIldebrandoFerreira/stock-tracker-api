package com.brando.stocktracker.controller;

import com.brando.stocktracker.controller.request.StockAddPurchaseRequest;
import com.brando.stocktracker.controller.request.StoqueRequest;
import com.brando.stocktracker.entity.Stock;
import com.brando.stocktracker.entity.StockPurchase;
import com.brando.stocktracker.mapper.StockMapper;
import com.brando.stocktracker.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<Stock> savePurchase(@RequestBody StoqueRequest request) {
        Pair<Stock, StockPurchase> stock = StockMapper.toSrock(request);
        return ResponseEntity.ok(stockService.savePurchase(stock.getFirst(), stock.getSecond()));
    }

    @PostMapping("/add")
    public ResponseEntity<Stock> addPurchase(@RequestBody StockAddPurchaseRequest request) {
        try {
            return ResponseEntity.ok(stockService.addPurchase(request.getStockId(), StockMapper.toStockPurchase(request)));
        }catch (IllegalArgumentException e){
            return ResponseEntity.unprocessableEntity().build();
        }

    }
}
