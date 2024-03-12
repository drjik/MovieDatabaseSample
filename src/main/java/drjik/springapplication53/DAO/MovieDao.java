package drjik.springapplication53.DAO;

import drjik.springapplication53.DTO.MovieFilter;
import drjik.springapplication53.Entity.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieDao extends EntityDao<Movie>{
    @Override
    public Movie getById(int id) {
        return em.find(Movie.class, id);
    }

    @Override
    public List<Movie> getAll() {
        return em.createQuery("from Movie", Movie.class).getResultList();
    }

    @Override
    public void update(Movie entity) {
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void insert(Movie entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Movie entity) {
        try {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
    }

    public List<Movie> filterByCriteria(MovieFilter movieFilter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> query = cb.createQuery(Movie.class);

        Root<Movie> root = query.from(Movie.class);

        List<Predicate> predicates = new ArrayList<>();
        if (movieFilter.getName() != null && !movieFilter.getName().equals("")) {
            predicates.add(cb.equal(root.get("name"), movieFilter.getName()));
        }
        if (movieFilter.getYearTo() != null) {
            predicates.add(cb.lt(root.get("year"), movieFilter.getYearTo()));
        }
        if (movieFilter.getYearFrom() != null) {
            predicates.add(cb.gt(root.get("year"), movieFilter.getYearFrom()));
        }
        if (movieFilter.getRating() != null) {
            predicates.add(cb.gt(root.get("rating"), movieFilter.getRating()));
        }
        if (movieFilter.getActorName() != null && !movieFilter.getActorName().equals("")) {
            Join<Movie, MoviesActors> actorsJoin = root.join("moviesActors", JoinType.LEFT);
            Join<MoviesActors, Actor> actorJoin = actorsJoin.join("actor", JoinType.LEFT);
            predicates.add(cb.equal(cb.lower(actorJoin.get("name")), movieFilter.getActorName().toLowerCase()));
        }
        if (movieFilter.getDirectorName() != null && !movieFilter.getDirectorName().equals("")) {
            Join<Movie, MoviesDirectors> directorsJoin = root.join("moviesDirectors", JoinType.LEFT);
            Join<MoviesDirectors, Director> directorJoin = directorsJoin.join("director", JoinType.LEFT);
            predicates.add(cb.equal(cb.lower(directorJoin.get("name")), movieFilter.getDirectorName().toLowerCase()));
        }
        query.select(root);

        TypedQuery<Movie> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
