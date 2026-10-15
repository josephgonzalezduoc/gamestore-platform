package com.gamestore.biblioteca.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gamestore.biblioteca.dto.CompradorDTO;

@FeignClient(name = "COMPRADOR-SERVICE")
public interface CompradorFeignClient {

    @GetMapping("/api/compradores/{id}")
    CompradorDTO obtenerComprador(@PathVariable("id") Long id);
}
