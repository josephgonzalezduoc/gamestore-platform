package com.gamestore.stock.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StockRequestDTO {
    @NotNull(message = "El juegoId es obligatorio")
    private Long juegoId;

    @NotNull(message = "La cantidad es obligatoria")
    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    private Integer cantidad;
}
