package com.gamestore.comprador.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.gamestore.comprador.dto.*;
import com.gamestore.comprador.service.CompradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/compradores")
@RequiredArgsConstructor
public class CompradorController {

    private final CompradorService compradorService;

    @GetMapping
    public ResponseEntity<List<CompradorResponseDTO>> listar() {
        return ResponseEntity.ok(compradorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompradorResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(compradorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CompradorResponseDTO> guardar(@Valid @RequestBody CompradorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compradorService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompradorResponseDTO> actualizar(@PathVariable Long id,
            @Valid @RequestBody CompradorRequestDTO dto) {
        return ResponseEntity.ok(compradorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        compradorService.delete(id);
        return ResponseEntity.ok(Map.of("mensaje", "Registro eliminado correctamente"));
    }
}
