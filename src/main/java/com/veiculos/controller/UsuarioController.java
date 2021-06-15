package com.veiculos.controller;

import com.veiculos.entity.Usuario;
import com.veiculos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity<List<Usuario>> listaUsuario() throws IOException {
        return ResponseEntity.ok(usuarioService.buscaUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> listaUsuario(@PathVariable(value = "id") Long id) throws IOException {
        return ResponseEntity.ok(usuarioService.buscaUsuarioPorId(id).get());
    }

    @PostMapping()
    public Object cadastraUsuario(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.cadastraUsuario(usuario));
    }

    @PostMapping("/{id_usuario}/{id_veiculo}")
    public Object adicionaVeiculoAoUsuario(@PathVariable(value = "id_usuario") Long id_usuario,
                                           @PathVariable(value = "id_veiculo") Long id_veiculo) throws IOException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.adicionaVeiculoAoUsuario(id_usuario, id_veiculo));
    }
}
