package br.com.caelum.ingresso.dao;

import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SessaoDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Sessao sessao) {
        entityManager.persist(sessao);
    }

    public List<Sessao> buscaSessoesDaSala(Sala sala) {
        return entityManager.createQuery("select s from Sessao s where s.sala = :sala")
                .setParameter("sala", sala)
                .getResultList();
    }
}
