package com.gamestore.compra.service;

import java.util.List;
import com.gamestore.compra.dto.*;

public interface CompraService {
    List<CompraResponseDTO> findAll();
    CompraResponseDTO findById(Long id);
    List<CompraResponseDTO> findByCompradorId(Long compradorId);
    CompraResponseDTO save(CompraRequestDTO dto);
    CompraResponseDTO updateEstado(Long id, String estado);
    void delete(Long id);
}
