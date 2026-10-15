package com.gamestore.compra.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compra")
public class CompraModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long compradorId;
    private Long juegoId;
    private LocalDateTime fecha;
    private String estado;
    private Double total;
}
