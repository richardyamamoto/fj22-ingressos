package br.com.caelum.ingresso.model.form;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public class SessaoForm {

    private Integer id;

    @NotNull
    private Integer salaId;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull
    private LocalTime horario;

    @NotNull
    private Integer filmeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSalaId() {
        return salaId;
    }

    public void setSalaId(Integer salaId) {
        this.salaId = salaId;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Integer getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(Integer filmeId) {
        this.filmeId = filmeId;
    }

    public Sessao toSessao(SalaDao salaDao, FilmeDao filmeDao) {
        Filme filme = filmeDao.findOne(filmeId);
        Sala sala = salaDao.findOne(salaId);
        Sessao sessao = new Sessao(horario, sala, filme);
        sessao.setId(id);
        return sessao;
    }
}
