package com.gamestore.compra.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.gamestore.compra.dto.*;
import com.gamestore.compra.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {
    private final CompraService compraService;

    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>> listar() {
        return ResponseEntity.ok(compraService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.findById(id));
    }

    @GetMapping("/comprador/{compradorId}")
    public ResponseEntity<List<CompraResponseDTO>> listarPorComprador(@PathVariable Long compradorId) {
        return ResponseEntity.ok(compraService.findByCompradorId(compradorId));
    }

    @PostMapping
    public ResponseEntity<CompraResponseDTO> guardar(@Valid @RequestBody CompraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compraService.save(dto));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<CompraResponseDTO> cambiarEstado(@PathVariable Long id,
            @Valid @RequestBody CompraEstadoDTO dto) {
        return ResponseEntity.ok(compraService.updateEstado(id, dto.getEstado()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        compraService.delete(id);
        return ResponseEntity.ok(Map.of("mensaje", "Registro eliminado correctamente"));
    }
}
