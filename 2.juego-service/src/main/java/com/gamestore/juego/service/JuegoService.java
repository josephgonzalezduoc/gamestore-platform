package com.gamestore.juego.service;

import java.util.List;
import com.gamestore.juego.dto.*;

public interface JuegoService {
    List<JuegoResponseDTO> findAll();
    JuegoResponseDTO findById(Long id);
    JuegoResponseDTO save(JuegoRequestDTO dto);
    JuegoResponseDTO update(Long id, JuegoRequestDTO dto);
    void delete(Long id);
}
