package com.veiculos.service;

import com.google.gson.Gson;
import com.veiculos.entity.Veiculo;
import com.veiculos.entity.VeiculoFIPE;
import com.veiculos.exceptions.ResourcesNotFoundException;
import com.veiculos.repository.VeiculoRepository;
import com.veiculos.utils.FIPEHttpRequest;
import com.veiculos.utils.UtilitarioDias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Object cadastraVeiculo(Veiculo veiculo) throws IOException {
        if(veiculo.getTipoCombustivel() == null) {
            veiculo.setTipoCombustivel(Veiculo.TipoCombustivel.NAO_INFORMADO);
        }
        VeiculoFIPE veiculoFIPE = buscaVeiculoFIPE(veiculo);
        veiculo.setValor(veiculoFIPE.getValor());
        veiculo.setMarca(veiculoFIPE.getMarca());
        veiculo.setModelo(veiculoFIPE.getModelo());
        veiculo.setDiaRodizio(UtilitarioDias.getDiaRodizio(veiculo.getAno().substring(3)));

        return veiculoRepository.save(veiculo);
    }

    public List<Veiculo> buscaVeiculos() throws IOException {
        List<Veiculo> veiculos = veiculoRepository.findAll();

        for(Veiculo veiculo : veiculos) {
            veiculo.setValor(buscaValorVeiculoFIPE(veiculo));
        }
        return veiculos;
    }

    public Optional<Veiculo> buscaVeiculoPorId(Long id_veiculo) throws IOException {

        Optional<Veiculo> veiculo = veiculoRepository.findById(id_veiculo);

        if (veiculo.isPresent()) {
            veiculo.get().setValor(buscaValorVeiculoFIPE(veiculo.get()));
            return veiculo;
        } else {
            throw new ResourcesNotFoundException("Veículo não encontrado.");
        }
    }

    public VeiculoFIPE buscaVeiculoFIPE (Veiculo veiculo) throws IOException {

        String urlString = "https://parallelum.com.br/fipe/api/v1/carros" +
                "/marcas/" + veiculo.getIdMarca() +
                "/modelos/" + veiculo.getIdModelo() +
                "/anos/" + veiculo.getAno() + "-" +
                veiculo.getTipoCombustivel().ordinal();

        URL url = new URL(urlString);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

        conexao.setRequestMethod("GET");
        conexao.setRequestProperty("Accept", "application/json");

        String resposta = FIPEHttpRequest.getRespostaApi(conexao);

        conexao.disconnect();

        Gson gson = new Gson();

        return gson.fromJson(new String(resposta.getBytes()), VeiculoFIPE.class);
    }

    public String buscaValorVeiculoFIPE(Veiculo veiculo) throws IOException {
        return buscaVeiculoFIPE(veiculo).getValor();
    }
}
