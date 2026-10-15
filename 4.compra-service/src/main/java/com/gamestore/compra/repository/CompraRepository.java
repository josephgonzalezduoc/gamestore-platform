package com.gamestore.compra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamestore.compra.model.CompraModel;

@Repository
public interface CompraRepository extends JpaRepository<CompraModel, Long> {

    List<CompraModel> findByCompradorId(Long compradorId);
}
