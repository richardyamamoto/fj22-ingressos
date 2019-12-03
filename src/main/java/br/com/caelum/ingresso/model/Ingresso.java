package br.com.caelum.ingresso.model;

import br.com.caelum.ingresso.desconto.Desconto;

import java.math.BigDecimal;

public class Ingresso {
    private Sessao sessao;
    private BigDecimal preco;

    /**
     * @deprecated hibernate only
     */
    public Ingresso() {
    }

    public Ingresso(Sessao sessao, Desconto tipoDeDesconto) {
        this.sessao = sessao;
        this.preco = tipoDeDesconto.aplicarDescontoSobre(sessao.getPreco());
    }

    public Sessao getSessao() {
        return sessao;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
