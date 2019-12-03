package br.com.caelum.ingresso.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class Sessao {

    @Id
    @GeneratedValue
    private Integer id;
    private LocalTime horario;
    @ManyToOne
    private Sala sala;
    @ManyToOne
    private Filme filme;
    private BigDecimal preco;

    @OneToMany(mappedBy = "sessao", fetch = FetchType.EAGER)
    private Set<Ingresso> ingressos = new HashSet<>();

    /**
     * @deprecated hibernate only
     */
    public Sessao() {}

    public Sessao(LocalTime horario, Sala sala, Filme filme) {
        this.horario = horario;
        this.sala = sala;
        this.filme = filme;
        this.preco = sala.getPreco().add(filme.getPreco());
    }

    public boolean isDisponivel(Lugar lugarSelecionado) {
        return ingressos.stream().map(Ingresso::getLugar).noneMatch(lugar -> lugar.equals(lugarSelecionado));
    }

    public Set<Ingresso> getIngressos() {
        return ingressos;
    }

    public void setIngressos(Set<Ingresso> ingressos) {
        this.ingressos = ingressos;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public LocalTime getHorarioTermino() {
        return this.horario.plusMinutes(filme.getDuracao().toMinutes());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Map<String, List<Lugar>> getMapaDeLugares() {
        return sala.getMapaDeLugares();
    }
}
