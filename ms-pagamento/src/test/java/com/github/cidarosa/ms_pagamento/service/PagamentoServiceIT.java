package com.github.cidarosa.ms_pagamento.service;

import com.github.cidarosa.ms_pagamento.repository.PagamentoRepository;
import com.github.cidarosa.ms_pagamento.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest // carrega o contexto
@Transactional // Rollback no DB

public class PagamentoServiceIT {

    @Autowired
    private PagamentoService service;
    @Autowired
    private PagamentoRepository repository;

    private Long existinId;
    private Long nonExistinId;
    private Long countTotalPagamento;

    @BeforeEach
    void setup() throws Exception {
        existinId = 1L;
        nonExistinId = 100L;
        countTotalPagamento = 6L;
    }

    @Test
    public void deletePagamentoShouldDoNotingWhenIdExists() {

        service.deletePagamento(existinId);
        Assertions.assertEquals(countTotalPagamento - 1, repository.count());
    }

    @Test
    public void deletePagamentoShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
                    service.deletePagamento(nonExistinId);
                });
    }
    @Test
    public void getAllShouldReturnListPagamentoDTO() {

        var result = service.getAll();
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countTotalPagamento, result.size());
        Assertions.assertEquals(Double.valueOf(35.55), result.get(0).getValor().doubleValue());
        Assertions.assertEquals("Amadeus Mozart", result.get(0).getNome());
        Assertions.assertEquals("Chiquinha Gonzaga", result.get(1).getNome());
        Assertions.assertNull(result.get(5).getNome());
        Assertions.assertEquals(null, result.get(5).getNome());

    }
}
