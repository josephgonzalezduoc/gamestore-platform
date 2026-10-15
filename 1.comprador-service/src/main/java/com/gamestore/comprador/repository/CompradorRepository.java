package com.gamestore.comprador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamestore.comprador.model.CompradorModel;

@Repository
public interface CompradorRepository extends JpaRepository<CompradorModel, Long> {

    boolean existsByCorreo(String correo);
}
