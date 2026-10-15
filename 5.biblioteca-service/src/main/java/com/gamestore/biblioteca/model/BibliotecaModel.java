package com.gamestore.biblioteca.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "biblioteca",
       uniqueConstraints = @UniqueConstraint(columnNames = {"comprador_id", "juego_id"}))
public class BibliotecaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comprador_id", nullable = false)
    private Long compradorId;

    @Column(name = "juego_id", nullable = false)
    private Long juegoId;

    private String tituloJuego;
    private LocalDateTime fechaAdquisicion;
}
