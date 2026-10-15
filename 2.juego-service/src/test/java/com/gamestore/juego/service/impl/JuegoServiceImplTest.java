package com.gamestore.juego.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.gamestore.juego.dto.*;
import com.gamestore.juego.exception.ResourceNotFoundException;
import com.gamestore.juego.model.JuegoModel;
import com.gamestore.juego.repository.JuegoRepository;

@ExtendWith(MockitoExtension.class)
class JuegoServiceImplTest {
    @Mock JuegoRepository repository;
    @InjectMocks JuegoServiceImpl service;

    private JuegoRequestDTO dto() {
        JuegoRequestDTO d=new JuegoRequestDTO(); d.setTitulo("Hades"); d.setPlataforma("PC"); d.setGenero("Roguelike"); d.setPrecio(12990.0); return d;
    }

    @Test void guardarJuego() {
        when(repository.save(any())).thenAnswer(i -> { JuegoModel j=i.getArgument(0); j.setId(1L); return j; });
        JuegoResponseDTO r=service.save(dto());
        assertEquals("Hades",r.getTitulo()); assertEquals(12990.0,r.getPrecio());
    }

    @Test void buscarInexistente() {
        when(repository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(9L));
    }
}
