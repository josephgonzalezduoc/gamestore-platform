package com.gamestore.compra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gamestore.compra.dto.StockDTO;

@FeignClient(name = "STOCK-SERVICE")
public interface StockFeignClient {

    @PutMapping("/api/stock/juego/{juegoId}/descontar")
    StockDTO descontar(@PathVariable("juegoId") Long juegoId,
                       @RequestParam("cantidad") Integer cantidad);

    @PutMapping("/api/stock/juego/{juegoId}/reponer")
    StockDTO reponer(@PathVariable("juegoId") Long juegoId,
                     @RequestParam("cantidad") Integer cantidad);
}
