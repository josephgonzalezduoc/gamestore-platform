package com.gamestore.compra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BibliotecaRequestDTO {
    private Long compradorId;
    private Long juegoId;
}
