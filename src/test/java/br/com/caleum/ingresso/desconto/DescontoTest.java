package br.com.caleum.ingresso.desconto;

import br.com.caelum.ingresso.desconto.DescontoDeTrintaPorCentoParaBancos;
import br.com.caelum.ingresso.desconto.DescontoEstudante;
import br.com.caelum.ingresso.desconto.SemDesconto;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;

public class DescontoTest {

    @Test
    public void deveConcederDescontoDe30PorcentoParaIngressosDeClientesDeBanco() {
        Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.50"));
        Filme filme = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12"));
        Sessao sessao = new Sessao(LocalTime.parse("10:00:00"),sala, filme);
        Ingresso ingresso = new Ingresso(sessao, new DescontoDeTrintaPorCentoParaBancos());
        BigDecimal precoEsperado = new BigDecimal("22.75");
        Assert.assertEquals(precoEsperado, ingresso.getPreco().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void deveConceder50PorcentoDeDescontoParaEstudante() {
        Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.50"));
        Filme filme = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12"));
        Sessao sessao = new Sessao(LocalTime.parse("10:00:00"),sala, filme);
        Ingresso ingresso = new Ingresso(sessao, new DescontoEstudante());
        BigDecimal precoEsperado = new BigDecimal("16.25");
        Assert.assertEquals(precoEsperado, ingresso.getPreco().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void naoDeveConcederDescontoParaIngressoNormal() {
        Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.50"));
        Filme filme = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12"));
        Sessao sessao = new Sessao(LocalTime.parse("10:00:00"),sala, filme);
        Ingresso ingresso = new Ingresso(sessao, new SemDesconto());
        BigDecimal precoEsperado = new BigDecimal("32.50");
        Assert.assertEquals(precoEsperado, ingresso.getPreco().setScale(2, RoundingMode.HALF_UP));
    }

}