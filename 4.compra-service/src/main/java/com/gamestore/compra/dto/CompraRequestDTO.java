package com.gamestore.compra.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompraRequestDTO {
    @NotNull(message = "El compradorId es obligatorio")
    private Long compradorId;

    @NotNull(message = "El juegoId es obligatorio")
    private Long juegoId;
}
