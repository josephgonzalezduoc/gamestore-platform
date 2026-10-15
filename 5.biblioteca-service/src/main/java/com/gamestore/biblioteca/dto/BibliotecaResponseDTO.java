package com.gamestore.biblioteca.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BibliotecaResponseDTO {
    private Long id;
    private Long compradorId;
    private Long juegoId;
    private String tituloJuego;
    private LocalDateTime fechaAdquisicion;
}
