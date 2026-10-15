package com.gamestore.biblioteca.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gamestore.biblioteca.dto.JuegoDTO;

@FeignClient(name = "JUEGO-SERVICE")
public interface JuegoFeignClient {

    @GetMapping("/api/juegos/{id}")
    JuegoDTO obtenerJuego(@PathVariable("id") Long id);
}
