package com.gamestore.biblioteca.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BibliotecaRequestDTO {
    @NotNull(message = "El compradorId es obligatorio")
    private Long compradorId;

    @NotNull(message = "El juegoId es obligatorio")
    private Long juegoId;
}
