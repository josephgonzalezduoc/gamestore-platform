package com.gamestore.comprador.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.gamestore.comprador.dto.*;
import com.gamestore.comprador.exception.*;
import com.gamestore.comprador.model.CompradorModel;
import com.gamestore.comprador.repository.CompradorRepository;
import com.gamestore.comprador.service.CompradorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompradorServiceImpl implements CompradorService {

    private final CompradorRepository compradorRepository;

    @Override
    public List<CompradorResponseDTO> findAll() {
        return compradorRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public CompradorResponseDTO findById(Long id) {
        return toResponse(buscar(id));
    }

    @Override
    public CompradorResponseDTO save(CompradorRequestDTO dto) {
        if (compradorRepository.existsByCorreo(dto.getCorreo())) {
            throw new ConflictException("Ya existe un comprador con el correo: " + dto.getCorreo());
        }

        CompradorModel comprador = new CompradorModel();
        comprador.setNombre(dto.getNombre());
        comprador.setCorreo(dto.getCorreo());
        comprador.setFechaRegistro(LocalDateTime.now());

        log.info("Guardando comprador {}", dto.getCorreo());
        return toResponse(compradorRepository.save(comprador));
    }

    @Override
    public CompradorResponseDTO update(Long id, CompradorRequestDTO dto) {
        CompradorModel comprador = buscar(id);

        if (!comprador.getCorreo().equalsIgnoreCase(dto.getCorreo())
                && compradorRepository.existsByCorreo(dto.getCorreo())) {
            throw new ConflictException("Ya existe un comprador con el correo: " + dto.getCorreo());
        }

        comprador.setNombre(dto.getNombre());
        comprador.setCorreo(dto.getCorreo());
        return toResponse(compradorRepository.save(comprador));
    }

    @Override
    public void delete(Long id) {
        compradorRepository.delete(buscar(id));
    }

    private CompradorModel buscar(Long id) {
        return compradorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comprador no encontrado con id: " + id));
    }

    private CompradorResponseDTO toResponse(CompradorModel c) {
        return new CompradorResponseDTO(c.getId(), c.getNombre(), c.getCorreo(), c.getFechaRegistro());
    }
}
