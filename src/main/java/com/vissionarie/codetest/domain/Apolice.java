package com.vissionarie.codetest.domain;

import com.vissionarie.codetest.service.dto.ApoliceDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Apolice.
 */
@Document(collection = "apolice")
public class Apolice implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final long MAX_BOUND = Long.MAX_VALUE / 1000;

    @Id
    private String id;

    @Field("numero")
    @Indexed
    private Long numero;

    @NotNull
    @Field("inicio")
    private LocalDate inicio;

    @NotNull
    @Field("fim")
    private LocalDate fim;

    @NotNull
    @Field("placa_veiculo")
    private String placaVeiculo;

    @Field("valor")
    private BigDecimal valor;

    public Apolice() {}

    public Apolice(ApoliceDTO apoliceDTO) {
        id = apoliceDTO.getId();
        numero = apoliceDTO.getNumero();
        inicio = apoliceDTO.getInicio();
        fim = apoliceDTO.getFim();
        placaVeiculo = apoliceDTO.getPlacaVeiculo();
        valor = apoliceDTO.getValor();
    }

    public Long generateNumero() {
        setNumero(ThreadLocalRandom.current().nextLong(0, MAX_BOUND));
        return this.numero;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Apolice id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNumero() {
        return this.numero;
    }

    public Apolice numero(Long numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public LocalDate getInicio() {
        return this.inicio;
    }

    public Apolice inicio(LocalDate inicio) {
        this.setInicio(inicio);
        return this;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return this.fim;
    }

    public Apolice fim(LocalDate fim) {
        this.setFim(fim);
        return this;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public String getPlacaVeiculo() {
        return this.placaVeiculo;
    }

    public Apolice placaVeiculo(String placaVeiculo) {
        this.setPlacaVeiculo(placaVeiculo);
        return this;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public Apolice valor(BigDecimal valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Apolice)) {
            return false;
        }
        return id != null && id.equals(((Apolice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Apolice{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", inicio='" + getInicio() + "'" +
            ", fim='" + getFim() + "'" +
            ", placaVeiculo='" + getPlacaVeiculo() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
