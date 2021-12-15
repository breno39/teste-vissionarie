package com.vissionarie.codetest.service.dto;

import com.vissionarie.codetest.domain.Apolice;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class ApoliceDTO {

    @Id
    private String id;

    private Long numero;

    @NotNull
    private LocalDate inicio;

    @NotNull
    private LocalDate fim;

    @NotNull
    @Field("placa_veiculo")
    private String placaVeiculo;

    @Field("valor")
    @NotNull
    private BigDecimal valor;

    public Apolice toEntity() {
        return new Apolice(this);
    }

    public Long getNumero() {
        return numero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
