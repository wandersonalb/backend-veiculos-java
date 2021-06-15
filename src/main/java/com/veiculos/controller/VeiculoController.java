package com.veiculos.controller;

import com.veiculos.entity.Veiculo;
import com.veiculos.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/veiculo")
@CrossOrigin(origins = "*")
public class VeiculoController {

    @Autowired
    VeiculoService veiculoService;

    @GetMapping()
    public ResponseEntity<List<Veiculo>> listaVeiculos() throws IOException {
        return ResponseEntity.ok(veiculoService.buscaVeiculos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> listaVeiculo(@PathVariable(value = "id") Long id) throws IOException {
        return ResponseEntity.ok(veiculoService.buscaVeiculoPorId(id).get());
    }

    @PostMapping()
    public Object cadastraVeiculo(@Valid @RequestBody Veiculo veiculo) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.cadastraVeiculo(veiculo));
    }
}
