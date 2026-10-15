package com.gamestore.juego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamestore.juego.model.JuegoModel;

@Repository
public interface JuegoRepository extends JpaRepository<JuegoModel, Long> {
}
