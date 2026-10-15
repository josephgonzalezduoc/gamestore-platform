package com.gamestore.biblioteca.service;

import java.util.List;
import com.gamestore.biblioteca.dto.*;

public interface BibliotecaService {
    List<BibliotecaResponseDTO> findAll();
    BibliotecaResponseDTO findById(Long id);
    List<BibliotecaResponseDTO> findByCompradorId(Long compradorId);
    boolean poseeJuego(Long compradorId, Long juegoId);
    BibliotecaResponseDTO save(BibliotecaRequestDTO dto);
    BibliotecaResponseDTO update(Long id, BibliotecaRequestDTO dto);
    void delete(Long id);
}
