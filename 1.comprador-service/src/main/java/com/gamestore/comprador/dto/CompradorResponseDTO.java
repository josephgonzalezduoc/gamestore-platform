package com.gamestore.comprador.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompradorResponseDTO {
    private Long id;
    private String nombre;
    private String correo;
    private LocalDateTime fechaRegistro;
}
