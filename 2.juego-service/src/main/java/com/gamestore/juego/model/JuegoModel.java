package com.gamestore.juego.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "juego")
public class JuegoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String plataforma;
    private String genero;
    private Double precio;
    @Column(length = 500)
    private String descripcion;
}
