package com.gamestore.juego.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JuegoResponseDTO {
    private Long id;
    private String titulo;
    private String plataforma;
    private String genero;
    private Double precio;
    private String descripcion;
}
