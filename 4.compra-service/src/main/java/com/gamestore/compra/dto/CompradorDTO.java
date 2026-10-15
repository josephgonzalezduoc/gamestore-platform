package com.gamestore.compra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompradorDTO {
    private Long id;
    private String nombre;
    private String correo;
}
