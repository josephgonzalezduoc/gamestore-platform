package com.gamestore.compra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gamestore.compra.dto.CompradorDTO;

@FeignClient(name = "COMPRADOR-SERVICE")
public interface CompradorFeignClient {

    @GetMapping("/api/compradores/{id}")
    CompradorDTO obtenerComprador(@PathVariable("id") Long id);
}
