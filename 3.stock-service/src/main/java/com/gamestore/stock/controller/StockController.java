package com.gamestore.stock.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.gamestore.stock.dto.*;
import com.gamestore.stock.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping
    public ResponseEntity<List<StockResponseDTO>> listar() {
        return ResponseEntity.ok(stockService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(stockService.findById(id));
    }

    @GetMapping("/juego/{juegoId}")
    public ResponseEntity<StockResponseDTO> buscarPorJuego(@PathVariable Long juegoId) {
        return ResponseEntity.ok(stockService.findByJuegoId(juegoId));
    }

    @PostMapping
    public ResponseEntity<StockResponseDTO> guardar(@Valid @RequestBody StockRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockResponseDTO> actualizar(@PathVariable Long id,
            @Valid @RequestBody StockRequestDTO dto) {
        return ResponseEntity.ok(stockService.update(id, dto));
    }

    @PutMapping("/juego/{juegoId}/descontar")
    public ResponseEntity<StockResponseDTO> descontar(@PathVariable Long juegoId,
            @RequestParam(defaultValue = "1") Integer cantidad) {
        return ResponseEntity.ok(stockService.descontar(juegoId, cantidad));
    }

    @PutMapping("/juego/{juegoId}/reponer")
    public ResponseEntity<StockResponseDTO> reponer(@PathVariable Long juegoId,
            @RequestParam(defaultValue = "1") Integer cantidad) {
        return ResponseEntity.ok(stockService.reponer(juegoId, cantidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        stockService.delete(id);
        return ResponseEntity.ok(Map.of("mensaje", "Registro eliminado correctamente"));
    }
}
