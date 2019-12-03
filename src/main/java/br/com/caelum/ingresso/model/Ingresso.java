package br.com.caelum.ingresso.model;

import br.com.caelum.ingresso.desconto.Desconto;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Ingresso {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    private Sessao sessao;
    private BigDecimal preco;
    @ManyToOne
    private Lugar lugar;
    @Enumerated(EnumType.STRING)
    private TipoDeIngresso tipoDeIngresso;

    /**
     * @deprecated hibernate only
     */
    public Ingresso() {
    }

    public Ingresso(Sessao sessao, Lugar lugar, TipoDeIngresso tipoDeIngresso) {
        this.sessao = sessao;
        this.tipoDeIngresso = tipoDeIngresso;
        this.preco = this.tipoDeIngresso.aplicaDesconto(sessao.getPreco());
        this.lugar = lugar;
    }


    public Sessao getSessao() {
        return sessao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public TipoDeIngresso getTipoDeIngresso() {
        return tipoDeIngresso;
    }

    public void setTipoDeIngresso(TipoDeIngresso tipoDeIngresso) {
        this.tipoDeIngresso = tipoDeIngresso;
    }
}
