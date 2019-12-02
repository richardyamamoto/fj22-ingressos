package br.com.caleum.ingresso.validacao;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.validacao.GerenciadorDeSessao;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GerenciadorDeSessaoTest {
    private Filme filme = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
    private Sala sala = new Sala("Sala 01", BigDecimal.ONE);
    private LocalTime horario = LocalTime.parse("20:00:00");

    @Test
    public void devePermitirInserirSessaoQuandoListaDeSessoesVazia() {
        List<Sessao>sessoes = Collections.emptyList();
        GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(sessoes);
//        Sala sala = new Sala("Sala 01");
//        Filme filme = new Filme("Rogue One", Duration.ofMinutes(120),"SCI-FI");
        Sessao sessao = new Sessao(this.horario, this.sala, this.filme);
        Assert.assertTrue(gerenciadorDeSessao.cabe(sessao));
    }

    @Test
    public void naoPermitirCriarSessoesNoMesmoHorario() {
//        Filme filme = new Filme("Rogue One", Duration.ofMinutes(120),"SCI-FI");
//        LocalTime horario = LocalTime.parse("20:00:00");
//        Sala sala = new Sala("Sala 01");
        Sessao sessao = new Sessao(this.horario, this.sala, this.filme);
        List<Sessao>sessoes = Arrays.asList(sessao);
        GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(sessoes);
        Assert.assertFalse(gerenciadorDeSessao.cabe(sessao));
    }

    @Test
    public void naoPermitirCriarSessoesTerminandoDentroDoHorarioDeUmaSessaoExistente() {
//        LocalTime horario = LocalTime.parse("20:00:00");
        Sessao sessao01 = new Sessao(this.horario,this.sala, this.filme);
        Sessao sessao02 = new Sessao(this.horario.minusHours(1), this.sala, this.filme);
        List<Sessao>sessoes = Arrays.asList(sessao01);
        GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(sessoes);
        Assert.assertFalse(gerenciadorDeSessao.cabe(sessao02));
    }

    @Test
    public void naoPermitirSessoesIniciandoDentroDeSessaoExistente() {
//        LocalTime horario = LocalTime.parse("20:00:00");
        Sessao sessao = new Sessao(this.horario, this.sala, this.filme);
        List<Sessao> sessoes = Arrays.asList(sessao);
        GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(sessoes);
        Assert.assertFalse(gerenciadorDeSessao.cabe(new Sessao(this.horario.plusHours(1), this.sala, this.filme)));
    }

    @Test
    public void permitirInsercaoEntreDoisFilmes() {
        Sessao sessao = new Sessao(this.horario, this.sala, this.filme);
        List<Sessao> sessoes = Arrays.asList(sessao);
        GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(sessoes);
        Assert.assertTrue(gerenciadorDeSessao.cabe(new Sessao(this.horario.plusMinutes(121), this.sala, this.filme)));
    }
}
