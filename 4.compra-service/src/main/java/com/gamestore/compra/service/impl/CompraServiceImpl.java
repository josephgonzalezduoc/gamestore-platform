package com.gamestore.compra.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gamestore.compra.dto.*;
import com.gamestore.compra.exception.*;
import com.gamestore.compra.feign.*;
import com.gamestore.compra.model.CompraModel;
import com.gamestore.compra.repository.CompraRepository;
import com.gamestore.compra.service.CompraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements CompraService {

    private static final Set<String> ESTADOS = Set.of("PAGADA", "CANCELADA", "REEMBOLSADA");

    private final CompraRepository compraRepository;
    private final CompradorFeignClient compradorFeignClient;
    private final JuegoFeignClient juegoFeignClient;
    private final StockFeignClient stockFeignClient;
    private final BibliotecaFeignClient bibliotecaFeignClient;

    @Override
    public List<CompraResponseDTO> findAll() {
        return compraRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public CompraResponseDTO findById(Long id) {
        return toResponse(buscar(id));
    }

    @Override
    public List<CompraResponseDTO> findByCompradorId(Long compradorId) {
        compradorFeignClient.obtenerComprador(compradorId);
        return compraRepository.findByCompradorId(compradorId).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public CompraResponseDTO save(CompraRequestDTO dto) {
        CompradorDTO comprador = compradorFeignClient.obtenerComprador(dto.getCompradorId());
        JuegoDTO juego = juegoFeignClient.obtenerJuego(dto.getJuegoId());

        log.info("{} intenta comprar '{}'", comprador.getNombre(), juego.getTitulo());

        if (Boolean.TRUE.equals(bibliotecaFeignClient.poseeJuego(dto.getCompradorId(), dto.getJuegoId()))) {
            throw new ConflictException("El comprador ya posee el juego '" + juego.getTitulo() + "' en su biblioteca");
        }

        stockFeignClient.descontar(dto.getJuegoId(), 1);

        try {
            CompraModel compra = new CompraModel();
            compra.setCompradorId(dto.getCompradorId());
            compra.setJuegoId(dto.getJuegoId());
            compra.setFecha(LocalDateTime.now());
            compra.setEstado("PAGADA");
            compra.setTotal(juego.getPrecio());

            CompraModel guardada = compraRepository.save(compra);
            bibliotecaFeignClient.agregarJuego(new BibliotecaRequestDTO(dto.getCompradorId(), dto.getJuegoId()));
            return toResponse(guardada);

        } catch (RuntimeException e) {
            log.error("La compra falló; se repone el stock del juego {}", dto.getJuegoId());
            try {
                stockFeignClient.reponer(dto.getJuegoId(), 1);
            } catch (RuntimeException ex) {
                log.error("No se pudo reponer el stock del juego {}", dto.getJuegoId());
            }
            throw e;
        }
    }

    @Override
    public CompraResponseDTO updateEstado(Long id, String estado) {
        if (estado == null || !ESTADOS.contains(estado.toUpperCase())) {
            throw new IllegalArgumentException("Estado inválido. Valores permitidos: " + ESTADOS);
        }

        CompraModel compra = buscar(id);
        compra.setEstado(estado.toUpperCase());
        return toResponse(compraRepository.save(compra));
    }

    @Override
    public void delete(Long id) {
        compraRepository.delete(buscar(id));
    }

    private CompraModel buscar(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra no encontrada con id: " + id));
    }

    private CompraResponseDTO toResponse(CompraModel c) {
        return new CompraResponseDTO(c.getId(), c.getCompradorId(), c.getJuegoId(), c.getFecha(), c.getEstado(), c.getTotal());
    }
}
