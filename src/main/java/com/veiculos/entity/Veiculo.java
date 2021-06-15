package com.veiculos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.veiculos.utils.UtilitarioDias;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idVeiculo;

    @NotNull
    @Min(value = 1, message = "Informe a marca do veículo")
    private int idMarca;

    @NotNull
    private String marca;

    @NotNull
    @Min(value = 1, message = "Informe o modelo do veículo")
    private int idModelo;

    @NotNull
    private String modelo;

    @Column(nullable = false)
    @Size(min = 4, max = 4)
    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "O ano informado está em um formato incorreto.")
    @NotBlank(message = "Informe o ano do veículo.")
    private String ano;

    @ManyToMany(mappedBy = "listaVeiculo", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Usuario> listaUsuario = new ArrayList<>();

    @Column(nullable = false)
    private int diaRodizio;

    @Transient
    private boolean rodizioAtivo;

    @Transient
    private String valor;

    public enum TipoCombustivel {
        NAO_INFORMADO,
        GASOLINA,
        ALCOOL,
        DIESEL
    }

    @Enumerated(EnumType.ORDINAL)
    private TipoCombustivel tipoCombustivel;

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(int idModelo) {
        this.idModelo = idModelo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }



    public void setDiaRodizio(int diaRodizio) {
        this.diaRodizio = diaRodizio;
    }

    public boolean isRodizioAtivo() {
        return rodizioAtivo;
    }

    public void setRodizioAtivo(boolean rodizioAtivo) {
        this.rodizioAtivo = rodizioAtivo;
    }

    public String getDiaRodizio() {
        return UtilitarioDias.getDiaExtenso(UtilitarioDias.getDiaRodizio(this.getAno().substring(3)));
    }

    public boolean getRodizioAtivo() {
        String dia_atual = new SimpleDateFormat("EEEE").format(new Date());
        return dia_atual.equals(this.getDiaRodizio());
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}
