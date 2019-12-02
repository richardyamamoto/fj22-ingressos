package br.com.caleum.ingresso.model;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

public class SessaoTest {

    @Test
    public void oPrecoDaSessaoDeveSerIgualSomaDoPrecoDaSalaMaisFilme() {
        Sala sala = new Sala("Eldorado - Imax", new BigDecimal("22.5"));
        Filme filme = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12.0"));
        BigDecimal somaPrecoSalaEFilme = sala.getPreco().add(filme.getPreco());

        Sessao sessao = new Sessao(LocalTime.parse("10:00:00"), sala, filme);
        Assert.assertEquals(somaPrecoSalaEFilme, sessao.getPreco());
    }
}
