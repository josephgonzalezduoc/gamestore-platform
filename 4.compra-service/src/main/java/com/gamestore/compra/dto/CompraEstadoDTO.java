package com.gamestore.compra.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraEstadoDTO {

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}
