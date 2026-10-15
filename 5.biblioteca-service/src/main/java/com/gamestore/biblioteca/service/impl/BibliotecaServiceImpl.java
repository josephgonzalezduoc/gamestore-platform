package com.gamestore.biblioteca.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.gamestore.biblioteca.dto.*;
import com.gamestore.biblioteca.exception.*;
import com.gamestore.biblioteca.feign.*;
import com.gamestore.biblioteca.model.BibliotecaModel;
import com.gamestore.biblioteca.repository.BibliotecaRepository;
import com.gamestore.biblioteca.service.BibliotecaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BibliotecaServiceImpl implements BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;
    private final CompradorFeignClient compradorFeignClient;
    private final JuegoFeignClient juegoFeignClient;

    @Override
    public List<BibliotecaResponseDTO> findAll() {
        return bibliotecaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public BibliotecaResponseDTO findById(Long id) {
        return toResponse(buscar(id));
    }

    @Override
    public List<BibliotecaResponseDTO> findByCompradorId(Long compradorId) {
        compradorFeignClient.obtenerComprador(compradorId);
        return bibliotecaRepository.findByCompradorId(compradorId).stream().map(this::toResponse).toList();
    }

    @Override
    public boolean poseeJuego(Long compradorId, Long juegoId) {
        return bibliotecaRepository.existsByCompradorIdAndJuegoId(compradorId, juegoId);
    }

    @Override
    public BibliotecaResponseDTO save(BibliotecaRequestDTO dto) {
        compradorFeignClient.obtenerComprador(dto.getCompradorId());
        JuegoDTO juego = juegoFeignClient.obtenerJuego(dto.getJuegoId());

        if (bibliotecaRepository.existsByCompradorIdAndJuegoId(dto.getCompradorId(), dto.getJuegoId())) {
            throw new ConflictException("El comprador ya posee este juego en su biblioteca");
        }

        BibliotecaModel item = new BibliotecaModel();
        item.setCompradorId(dto.getCompradorId());
        item.setJuegoId(dto.getJuegoId());
        item.setTituloJuego(juego.getTitulo());
        item.setFechaAdquisicion(LocalDateTime.now());

        log.info("Agregando '{}' a la biblioteca del comprador {}", juego.getTitulo(), dto.getCompradorId());
        return toResponse(bibliotecaRepository.save(item));
    }

    @Override
    public BibliotecaResponseDTO update(Long id, BibliotecaRequestDTO dto) {
        BibliotecaModel item = buscar(id);
        compradorFeignClient.obtenerComprador(dto.getCompradorId());
        JuegoDTO juego = juegoFeignClient.obtenerJuego(dto.getJuegoId());

        boolean cambio = !item.getCompradorId().equals(dto.getCompradorId())
                || !item.getJuegoId().equals(dto.getJuegoId());

        if (cambio && bibliotecaRepository.existsByCompradorIdAndJuegoId(dto.getCompradorId(), dto.getJuegoId())) {
            throw new ConflictException("El comprador ya posee este juego en su biblioteca");
        }

        item.setCompradorId(dto.getCompradorId());
        item.setJuegoId(dto.getJuegoId());
        item.setTituloJuego(juego.getTitulo());
        return toResponse(bibliotecaRepository.save(item));
    }

    @Override
    public void delete(Long id) {
        bibliotecaRepository.delete(buscar(id));
    }

    private BibliotecaModel buscar(Long id) {
        return bibliotecaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de biblioteca no encontrado con id: " + id));
    }

    private BibliotecaResponseDTO toResponse(BibliotecaModel b) {
        return new BibliotecaResponseDTO(b.getId(), b.getCompradorId(), b.getJuegoId(), b.getTituloJuego(), b.getFechaAdquisicion());
    }
}
