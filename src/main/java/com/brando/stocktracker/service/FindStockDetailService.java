package com.brando.stocktracker.service;

import com.brando.stocktracker.client.BrapiClient;
import com.brando.stocktracker.client.response.BrapiStockDataResponse;
import com.brando.stocktracker.client.response.BrapiStockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindStockDetailService {

    private final String token = "4pFNNjDK9TUM1JRGYzztDb";
    private final BrapiClient brapiClient;

    @Cacheable(value = "acao", key = "#stock")
    public Optional<BrapiStockDataResponse> getBrapiStockDetail(String stock) {
        log.info("Consultando informações da ação: {} na Brapi", stock);
        BrapiStockResponse brapiStockResponse =brapiClient.getStock(stock, token);
        log.info("Retorno Brapi da ação {}: {}", stock, brapiStockResponse.getResults().get(0));
        return  Optional.of(brapiStockResponse.getResults().get(0));
    }
}
