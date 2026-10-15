package com.gamestore.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamestore.biblioteca.model.BibliotecaModel;

@Repository
public interface BibliotecaRepository extends JpaRepository<BibliotecaModel, Long> {

    boolean existsByCompradorIdAndJuegoId(Long compradorId, Long juegoId);

    List<BibliotecaModel> findByCompradorId(Long compradorId);
}
