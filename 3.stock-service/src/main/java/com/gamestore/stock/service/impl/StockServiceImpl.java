package com.gamestore.stock.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gamestore.stock.dto.*;
import com.gamestore.stock.exception.*;
import com.gamestore.stock.feign.JuegoFeignClient;
import com.gamestore.stock.model.StockModel;
import com.gamestore.stock.repository.StockRepository;
import com.gamestore.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final JuegoFeignClient juegoFeignClient;

    @Override
    public List<StockResponseDTO> findAll() {
        return stockRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public StockResponseDTO findById(Long id) {
        return toResponse(buscar(id));
    }

    @Override
    public StockResponseDTO findByJuegoId(Long juegoId) {
        return toResponse(buscarPorJuego(juegoId));
    }

    @Override
    public StockResponseDTO save(StockRequestDTO dto) {
        juegoFeignClient.obtenerJuego(dto.getJuegoId());

        if (stockRepository.existsByJuegoId(dto.getJuegoId())) {
            throw new ConflictException("Ya existe stock registrado para el juego con id: " + dto.getJuegoId());
        }

        StockModel stock = new StockModel();
        stock.setJuegoId(dto.getJuegoId());
        stock.setCantidad(dto.getCantidad());
        log.info("Registrando stock del juego {}", dto.getJuegoId());
        return toResponse(stockRepository.save(stock));
    }

    @Override
    public StockResponseDTO update(Long id, StockRequestDTO dto) {
        StockModel stock = buscar(id);

        if (!stock.getJuegoId().equals(dto.getJuegoId())) {
            juegoFeignClient.obtenerJuego(dto.getJuegoId());
            if (stockRepository.existsByJuegoId(dto.getJuegoId())) {
                throw new ConflictException("Ya existe stock registrado para el juego con id: " + dto.getJuegoId());
            }
            stock.setJuegoId(dto.getJuegoId());
        }

        stock.setCantidad(dto.getCantidad());
        return toResponse(stockRepository.save(stock));
    }

    @Override
    @Transactional
    public StockResponseDTO descontar(Long juegoId, Integer cantidad) {
        validarCantidad(cantidad);
        StockModel stock = buscarPorJuego(juegoId);

        if (stock.getCantidad() < cantidad) {
            throw new ConflictException("Stock insuficiente para el juego " + juegoId
                    + ". Disponible: " + stock.getCantidad());
        }

        stock.setCantidad(stock.getCantidad() - cantidad);
        log.info("Se descontaron {} unidades del juego {}", cantidad, juegoId);
        return toResponse(stockRepository.save(stock));
    }

    @Override
    @Transactional
    public StockResponseDTO reponer(Long juegoId, Integer cantidad) {
        validarCantidad(cantidad);
        StockModel stock = buscarPorJuego(juegoId);
        stock.setCantidad(stock.getCantidad() + cantidad);
        log.info("Se repusieron {} unidades del juego {}", cantidad, juegoId);
        return toResponse(stockRepository.save(stock));
    }

    @Override
    public void delete(Long id) {
        stockRepository.delete(buscar(id));
    }

    private StockModel buscar(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock no encontrado con id: " + id));
    }

    private StockModel buscarPorJuego(Long juegoId) {
        return stockRepository.findByJuegoId(juegoId)
                .orElseThrow(() -> new ResourceNotFoundException("No hay stock registrado para el juego con id: " + juegoId));
    }

    private void validarCantidad(Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
    }

    private StockResponseDTO toResponse(StockModel s) {
        return new StockResponseDTO(s.getId(), s.getJuegoId(), s.getCantidad());
    }
}
