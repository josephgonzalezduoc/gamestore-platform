package com.gamestore.stock.service;

import java.util.List;
import com.gamestore.stock.dto.*;

public interface StockService {
    List<StockResponseDTO> findAll();
    StockResponseDTO findById(Long id);
    StockResponseDTO findByJuegoId(Long juegoId);
    StockResponseDTO save(StockRequestDTO dto);
    StockResponseDTO update(Long id, StockRequestDTO dto);
    StockResponseDTO descontar(Long juegoId, Integer cantidad);
    StockResponseDTO reponer(Long juegoId, Integer cantidad);
    void delete(Long id);
}
