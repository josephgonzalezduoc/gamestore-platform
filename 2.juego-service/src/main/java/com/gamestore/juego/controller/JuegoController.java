package com.gamestore.juego.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.gamestore.juego.dto.*;
import com.gamestore.juego.service.JuegoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/juegos")
@RequiredArgsConstructor
public class JuegoController {
    private final JuegoService juegoService;

    @GetMapping
    public ResponseEntity<List<JuegoResponseDTO>> listar() {
        return ResponseEntity.ok(juegoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuegoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(juegoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<JuegoResponseDTO> guardar(@Valid @RequestBody JuegoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(juegoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuegoResponseDTO> actualizar(@PathVariable Long id,
            @Valid @RequestBody JuegoRequestDTO dto) {
        return ResponseEntity.ok(juegoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        juegoService.delete(id);
        return ResponseEntity.ok(Map.of("mensaje", "Registro eliminado correctamente"));
    }
}
