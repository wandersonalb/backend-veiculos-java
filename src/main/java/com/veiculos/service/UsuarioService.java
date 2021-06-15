package com.veiculos.service;

import com.veiculos.entity.Usuario;
import com.veiculos.entity.Veiculo;
import com.veiculos.exceptions.ResourcesNotFoundException;
import com.veiculos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VeiculoService veiculoService;

    public Object cadastraUsuario(Usuario usuario) {
        if(buscaUsuarioPorCpf(usuario.getCpf()).isPresent()) {
            throw new ResourcesNotFoundException("Já existe um usuário para o cpf informado.");
        } else if(buscaUsuarioPorEmail(usuario.getEmail()).isPresent()) {
            throw new ResourcesNotFoundException("Já existe um usuário para o email informado.");
        } else if(usuario.getData_nascimento() == null) {
            throw new ResourcesNotFoundException("Informe a Data de nascimento.");
        } else {
            return usuarioRepository.save(usuario);
        }
    }

    @Transactional
    public Object adicionaVeiculoAoUsuario(Long id_usuario, Long id_veiculo) throws IOException {

        Optional<Usuario> usuario = this.buscaUsuarioPorId(id_usuario);
        Optional<Veiculo> veiculo = veiculoService.buscaVeiculoPorId(id_veiculo);

        usuario.get().getListaVeiculo().add(veiculo.get());
        veiculo.get().getListaUsuario().add(usuario.get());

        return usuario;
    }

    public List<Usuario> buscaUsuarios() throws IOException {
        List<Usuario> usuarios = usuarioRepository.findAll();

        for(Usuario usuario : usuarios) {
            for(Veiculo veiculo : usuario.getListaVeiculo()) {
                veiculo.setValor(veiculoService.buscaValorVeiculoFIPE(veiculo));
            }
        }

        return usuarios;
    }

    public Optional<Usuario> buscaUsuarioPorId(Long id_usuario) throws IOException {

        Optional<Usuario> usuario = usuarioRepository.findById(id_usuario);

        if (usuario.isPresent()) {
            for(Veiculo veiculo : usuario.get().getListaVeiculo()) {
                veiculo.setValor(veiculoService.buscaValorVeiculoFIPE(veiculo));
            }
            return usuario;
        } else {
            throw new ResourcesNotFoundException("Usuário não encontrado.");
        }
    }

    public Optional<Usuario> buscaUsuarioPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    public Optional<Usuario> buscaUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

}
