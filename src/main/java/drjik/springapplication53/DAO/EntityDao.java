package drjik.springapplication53.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public abstract class EntityDao<T> {
    @PersistenceContext
    protected EntityManager em;

    public abstract T getById(int id);
    public abstract List<T> getAll();
    public abstract void update(T entity);
    public abstract void insert(T entity);
    public abstract void delete(T entity);
}
