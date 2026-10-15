package com.gamestore.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockResponseDTO {
    private Long id;
    private Long juegoId;
    private Integer cantidad;
}
