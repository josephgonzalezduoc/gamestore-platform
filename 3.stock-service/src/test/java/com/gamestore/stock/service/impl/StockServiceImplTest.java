package com.gamestore.stock.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.gamestore.stock.dto.*;
import com.gamestore.stock.exception.ConflictException;
import com.gamestore.stock.feign.JuegoFeignClient;
import com.gamestore.stock.model.StockModel;
import com.gamestore.stock.repository.StockRepository;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {
    @Mock StockRepository repository;
    @Mock JuegoFeignClient juegoClient;
    @InjectMocks StockServiceImpl service;

    @Test void descontarStock() {
        StockModel s=new StockModel(1L,2L,5);
        when(repository.findByJuegoId(2L)).thenReturn(Optional.of(s));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        assertEquals(3, service.descontar(2L,2).getCantidad());
    }

    @Test void stockInsuficienteDaConflicto() {
        when(repository.findByJuegoId(2L)).thenReturn(Optional.of(new StockModel(1L,2L,1)));
        assertThrows(ConflictException.class, () -> service.descontar(2L,2));
    }

    @Test void cantidadCeroEsInvalida() {
        assertThrows(IllegalArgumentException.class, () -> service.descontar(2L,0));
    }
}
