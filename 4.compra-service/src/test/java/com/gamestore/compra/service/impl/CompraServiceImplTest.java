package com.gamestore.compra.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.gamestore.compra.dto.*;
import com.gamestore.compra.exception.ConflictException;
import com.gamestore.compra.feign.*;
import com.gamestore.compra.model.CompraModel;
import com.gamestore.compra.repository.CompraRepository;

@ExtendWith(MockitoExtension.class)
class CompraServiceImplTest {
    @Mock CompraRepository repository;
    @Mock CompradorFeignClient compradorClient;
    @Mock JuegoFeignClient juegoClient;
    @Mock StockFeignClient stockClient;
    @Mock BibliotecaFeignClient bibliotecaClient;
    @InjectMocks CompraServiceImpl service;

    private CompraRequestDTO dto() { CompraRequestDTO d=new CompraRequestDTO(); d.setCompradorId(1L); d.setJuegoId(2L); return d; }
    private void base() {
        when(compradorClient.obtenerComprador(1L)).thenReturn(new CompradorDTO(1L,"Ana","ana@mail.cl"));
        when(juegoClient.obtenerJuego(2L)).thenReturn(new JuegoDTO(2L,"Hades","PC","Roguelike",12990.0));
    }

    @Test void comprarRegistraYAgregaBiblioteca() {
        base(); when(bibliotecaClient.poseeJuego(1L,2L)).thenReturn(false);
        when(repository.save(any())).thenAnswer(i -> { CompraModel c=i.getArgument(0); c.setId(1L); return c; });
        CompraResponseDTO r=service.save(dto());
        assertEquals("PAGADA",r.getEstado()); assertEquals(12990.0,r.getTotal());
        verify(stockClient).descontar(2L,1); verify(bibliotecaClient).agregarJuego(any());
    }

    @Test void noPermiteJuegoDuplicado() {
        base(); when(bibliotecaClient.poseeJuego(1L,2L)).thenReturn(true);
        assertThrows(ConflictException.class, () -> service.save(dto()));
    }
}
