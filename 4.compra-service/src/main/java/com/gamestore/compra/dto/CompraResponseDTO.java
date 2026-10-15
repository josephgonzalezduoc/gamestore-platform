package com.gamestore.compra.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompraResponseDTO {
    private Long id;
    private Long compradorId;
    private Long juegoId;
    private LocalDateTime fecha;
    private String estado;
    private Double total;
}
