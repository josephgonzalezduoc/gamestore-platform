package com.gamestore.comprador.service;

import java.util.List;
import com.gamestore.comprador.dto.CompradorRequestDTO;
import com.gamestore.comprador.dto.CompradorResponseDTO;

public interface CompradorService {
    List<CompradorResponseDTO> findAll();
    CompradorResponseDTO findById(Long id);
    CompradorResponseDTO save(CompradorRequestDTO dto);
    CompradorResponseDTO update(Long id, CompradorRequestDTO dto);
    void delete(Long id);
}
