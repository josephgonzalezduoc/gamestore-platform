package com.gamestore.comprador.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.gamestore.comprador.dto.*;
import com.gamestore.comprador.exception.ConflictException;
import com.gamestore.comprador.model.CompradorModel;
import com.gamestore.comprador.repository.CompradorRepository;

@ExtendWith(MockitoExtension.class)
class CompradorServiceImplTest {
    @Mock CompradorRepository repository;
    @InjectMocks CompradorServiceImpl service;

    private CompradorRequestDTO dto() {
        CompradorRequestDTO d = new CompradorRequestDTO();
        d.setNombre("Ana"); d.setCorreo("ana@mail.cl"); return d;
    }

    @Test void guardarAsignaFecha() {
        when(repository.existsByCorreo("ana@mail.cl")).thenReturn(false);
        when(repository.save(any())).thenAnswer(i -> { CompradorModel c=i.getArgument(0); c.setId(1L); return c; });
        CompradorResponseDTO r=service.save(dto());
        assertEquals(1L,r.getId()); assertNotNull(r.getFechaRegistro());
    }

    @Test void correoDuplicadoDaConflicto() {
        when(repository.existsByCorreo("ana@mail.cl")).thenReturn(true);
        assertThrows(ConflictException.class, () -> service.save(dto()));
    }

    @Test void eliminarUsaRepositorio() {
        CompradorModel c=new CompradorModel(); c.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(c));
        service.delete(1L); verify(repository).delete(c);
    }
}
