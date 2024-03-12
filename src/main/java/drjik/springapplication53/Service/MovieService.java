package drjik.springapplication53.Service;

import drjik.springapplication53.DAO.MovieDao;
import drjik.springapplication53.DTO.MovieFilter;
import drjik.springapplication53.Entity.*;
import drjik.springapplication53.Repository.MovieRepository;
import drjik.springapplication53.Repository.MoviesActorsRepository;
import drjik.springapplication53.Repository.MoviesDirectorsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final MoviesActorsRepository moviesActorsRepository;
    private final MoviesDirectorsRepository moviesDirectorsRepository;

    @PersistenceContext
    EntityManager em;

    public MovieService(MovieRepository movieRepository, MoviesActorsRepository moviesActorsRepository, MoviesDirectorsRepository moviesDirectorsRepository, MovieDao movieDao) {
        this.movieRepository = movieRepository;
        this.moviesActorsRepository = moviesActorsRepository;
        this.moviesDirectorsRepository = moviesDirectorsRepository;
    }

    public Page<Movie> getPagesMovie(Integer numberPage) {
        Pageable pageable = PageRequest.of(numberPage, 5);

        return movieRepository.findAll(pageable);
    }

    public Movie getPageMovie(Integer id) {
        return movieRepository.findById(id).get();
    }

    public List<Movie> getMoviesTopTen() {
        return movieRepository.getMoviesByTopTen();
    }

    public void updateMovie(Integer id, String name, Integer year, Genre genre, Double rating) {
        Movie movie = new Movie();

        movie.setId(id);

        if (name != null) {
            movie.setName(name);
        }

        if (year != null) {
            movie.setYear(year);
        }

        if (genre != null) {
            movie.setGenre(genre);
        }

        if (rating != null) {
            movie.setRating(rating);
        }

        movieRepository.save(movie);
    }

    public void deleteMovie(Integer id) {
        moviesActorsRepository.deleteMoviesActorsByMovie(id);
        moviesDirectorsRepository.deleteMoviesActorsByMovie(id);
        movieRepository.deleteMoviesActorsByMovie(id);
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
        for (Predicate predicate : predicates) {
            query.where(predicate);
        }

        TypedQuery<Movie> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
