package com.henriq.libraryapi.repository;


import com.henriq.libraryapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TransactionServiceTest {
    @Autowired
    TransactionService service;
    @Test
    public void testAPI(){
        service.updateTransactionTest();
    }
}
