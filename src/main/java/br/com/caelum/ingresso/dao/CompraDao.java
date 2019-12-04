package br.com.caelum.ingresso.dao;

import br.com.caelum.ingresso.model.Compra;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CompraDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Compra compra) {
        entityManager.persist(compra);
    }
}
