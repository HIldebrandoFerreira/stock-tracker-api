package com.brando.stocktracker.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "stock")
public class Stock {
    @Id
    private String id;
    private String stock;
    @Transient
    private String price;
    @DBRef
    private List<StockPurchase> purchases;
    //private User user;
}
