package com.gamestore.biblioteca.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.gamestore.biblioteca.dto.*;
import com.gamestore.biblioteca.exception.ConflictException;
import com.gamestore.biblioteca.feign.*;
import com.gamestore.biblioteca.model.BibliotecaModel;
import com.gamestore.biblioteca.repository.BibliotecaRepository;

@ExtendWith(MockitoExtension.class)
class BibliotecaServiceImplTest {
    @Mock BibliotecaRepository repository;
    @Mock CompradorFeignClient compradorClient;
    @Mock JuegoFeignClient juegoClient;
    @InjectMocks BibliotecaServiceImpl service;

    private BibliotecaRequestDTO dto() { BibliotecaRequestDTO d=new BibliotecaRequestDTO(); d.setCompradorId(1L); d.setJuegoId(2L); return d; }

    @Test void agregarJuegoCopiaTituloYFecha() {
        when(juegoClient.obtenerJuego(2L)).thenReturn(new JuegoDTO(2L,"Hades"));
        when(repository.existsByCompradorIdAndJuegoId(1L,2L)).thenReturn(false);
        when(repository.save(any())).thenAnswer(i -> { BibliotecaModel b=i.getArgument(0); b.setId(1L); return b; });
        BibliotecaResponseDTO r=service.save(dto());
        assertEquals("Hades",r.getTituloJuego()); assertNotNull(r.getFechaAdquisicion());
    }

    @Test void duplicadoDaConflicto() {
        when(juegoClient.obtenerJuego(2L)).thenReturn(new JuegoDTO(2L,"Hades"));
        when(repository.existsByCompradorIdAndJuegoId(1L,2L)).thenReturn(true);
        assertThrows(ConflictException.class, () -> service.save(dto()));
    }
}
