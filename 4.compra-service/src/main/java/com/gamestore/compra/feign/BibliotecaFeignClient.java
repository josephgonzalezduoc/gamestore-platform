package com.gamestore.compra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gamestore.compra.dto.BibliotecaDTO;
import com.gamestore.compra.dto.BibliotecaRequestDTO;

@FeignClient(name = "BIBLIOTECA-SERVICE")
public interface BibliotecaFeignClient {

    @GetMapping("/api/biblioteca/comprador/{compradorId}/juego/{juegoId}/posee")
    Boolean poseeJuego(@PathVariable("compradorId") Long compradorId,
                       @PathVariable("juegoId") Long juegoId);

    @PostMapping("/api/biblioteca")
    BibliotecaDTO agregarJuego(@RequestBody BibliotecaRequestDTO request);
}
