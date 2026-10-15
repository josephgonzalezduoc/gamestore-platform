package com.gamestore.stock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamestore.stock.model.StockModel;

@Repository
public interface StockRepository extends JpaRepository<StockModel, Long> {

    Optional<StockModel> findByJuegoId(Long juegoId);

    boolean existsByJuegoId(Long juegoId);
}
