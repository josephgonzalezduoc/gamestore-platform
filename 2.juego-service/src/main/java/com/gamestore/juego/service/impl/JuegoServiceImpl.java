package com.gamestore.juego.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.gamestore.juego.dto.*;
import com.gamestore.juego.exception.ResourceNotFoundException;
import com.gamestore.juego.model.JuegoModel;
import com.gamestore.juego.repository.JuegoRepository;
import com.gamestore.juego.service.JuegoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JuegoServiceImpl implements JuegoService {

    private final JuegoRepository juegoRepository;

    @Override
    public List<JuegoResponseDTO> findAll() {
        return juegoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public JuegoResponseDTO findById(Long id) {
        return toResponse(buscar(id));
    }

    @Override
    public JuegoResponseDTO save(JuegoRequestDTO dto) {
        JuegoModel juego = new JuegoModel();
        copiar(dto, juego);
        log.info("Guardando juego {}", dto.getTitulo());
        return toResponse(juegoRepository.save(juego));
    }

    @Override
    public JuegoResponseDTO update(Long id, JuegoRequestDTO dto) {
        JuegoModel juego = buscar(id);
        copiar(dto, juego);
        return toResponse(juegoRepository.save(juego));
    }

    @Override
    public void delete(Long id) {
        juegoRepository.delete(buscar(id));
    }

    private JuegoModel buscar(Long id) {
        return juegoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Juego no encontrado con id: " + id));
    }

    private void copiar(JuegoRequestDTO dto, JuegoModel juego) {
        juego.setTitulo(dto.getTitulo());
        juego.setPlataforma(dto.getPlataforma());
        juego.setGenero(dto.getGenero());
        juego.setPrecio(dto.getPrecio());
        juego.setDescripcion(dto.getDescripcion());
    }

    private JuegoResponseDTO toResponse(JuegoModel j) {
        return new JuegoResponseDTO(j.getId(), j.getTitulo(), j.getPlataforma(), j.getGenero(), j.getPrecio(), j.getDescripcion());
    }
}
