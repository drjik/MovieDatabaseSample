package drjik.springapplication53.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public abstract class EntityDao<T> implements AutoCloseable{
    protected EntityManager em;

    protected EntityDao () {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        em = factory.createEntityManager();
    }

    public abstract T getById(int id);
    public abstract List<T> getAll();
    public abstract void update(T entity);
    public abstract void insert(T entity);
    public abstract void delete(T entity);

    @Override
    public void close() throws Exception {
        if (em.isOpen()) {
            em.close();
        }
    }
}
