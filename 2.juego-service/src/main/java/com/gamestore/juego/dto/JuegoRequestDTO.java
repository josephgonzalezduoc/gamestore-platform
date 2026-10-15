package com.gamestore.juego.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class JuegoRequestDTO {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La plataforma es obligatoria")
    private String plataforma;

    private String genero;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    private String descripcion;
}
