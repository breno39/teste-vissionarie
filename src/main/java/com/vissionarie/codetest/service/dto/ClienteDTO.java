package com.vissionarie.codetest.service.dto;

import com.vissionarie.codetest.domain.Cliente;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;

public class ClienteDTO {

    @Id
    private String id;

    @NotNull
    @Size(min = 11, max = 11)
    @CPF
    private String cpf;

    @NotNull
    @Size(min = 10)
    private String nome;

    @NotNull
    @Size(min = 2)
    private String cidade;

    @NotNull
    @Size(min = 2)
    private String estado;

    public Cliente toEntity() {
        return new Cliente(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
