package br.com.caelum.ingresso.model;

import br.com.caelum.ingresso.desconto.Desconto;
import br.com.caelum.ingresso.desconto.DescontoDeTrintaPorCentoParaBancos;
import br.com.caelum.ingresso.desconto.DescontoEstudante;
import br.com.caelum.ingresso.desconto.SemDesconto;

import java.math.BigDecimal;

public enum TipoDeIngresso {
    INTEIRO(new SemDesconto()),
    ESTUDANTE(new DescontoEstudante()),
    BANCO(new DescontoDeTrintaPorCentoParaBancos());

    private final Desconto desconto;

    TipoDeIngresso(Desconto desconto) {
        this.desconto = desconto;
    }

    public BigDecimal aplicaDesconto(BigDecimal valor) {
        return desconto.aplicarDescontoSobre(valor);
    }

    public String getDescricao() {
        return desconto.getDescricao();
    }
}
