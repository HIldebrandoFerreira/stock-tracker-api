package com.brando.stocktracker.controller;

import com.brando.stocktracker.controller.request.StockAddPurchaseRequest;
import com.brando.stocktracker.controller.request.StoqueRequest;
import com.brando.stocktracker.entity.Stock;
import com.brando.stocktracker.entity.StockPurchase;
import com.brando.stocktracker.mapper.StockMapper;
import com.brando.stocktracker.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<Stock> savePurchase(@RequestBody StoqueRequest request) {
        log.info("Cadastrar");
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

    @GetMapping
    ResponseEntity<List<Stock>> fyndAll() {
        log.info("Camou a rota de GET ALL");
        return ResponseEntity.ok(stockService.findAll());
    }
}
