package com.gamestore.compra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JuegoDTO {
    private Long id;
    private String titulo;
    private String plataforma;
    private String genero;
    private Double precio;
}
