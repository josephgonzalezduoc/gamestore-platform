package com.gamestore.biblioteca.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.gamestore.biblioteca.dto.*;
import com.gamestore.biblioteca.service.BibliotecaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/biblioteca")
@RequiredArgsConstructor
public class BibliotecaController {
    private final BibliotecaService bibliotecaService;

    @GetMapping
    public ResponseEntity<List<BibliotecaResponseDTO>> listar() {
        return ResponseEntity.ok(bibliotecaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliotecaResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecaService.findById(id));
    }

    @GetMapping("/comprador/{compradorId}")
    public ResponseEntity<List<BibliotecaResponseDTO>> listarPorComprador(@PathVariable Long compradorId) {
        return ResponseEntity.ok(bibliotecaService.findByCompradorId(compradorId));
    }

    @GetMapping("/comprador/{compradorId}/juego/{juegoId}/posee")
    public ResponseEntity<Boolean> poseeJuego(@PathVariable Long compradorId, @PathVariable Long juegoId) {
        return ResponseEntity.ok(bibliotecaService.poseeJuego(compradorId, juegoId));
    }

    @PostMapping
    public ResponseEntity<BibliotecaResponseDTO> guardar(@Valid @RequestBody BibliotecaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bibliotecaService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BibliotecaResponseDTO> actualizar(@PathVariable Long id,
            @Valid @RequestBody BibliotecaRequestDTO dto) {
        return ResponseEntity.ok(bibliotecaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        bibliotecaService.delete(id);
        return ResponseEntity.ok(Map.of("mensaje", "Registro eliminado correctamente"));
    }
}
