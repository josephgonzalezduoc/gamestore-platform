package com.gamestore.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JuegoDTO {
    private Long id;
    private String titulo;
    private Double precio;
}
