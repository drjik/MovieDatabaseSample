package drjik.springapplication53.Repository;

import drjik.springapplication53.Entity.Movie;
import drjik.springapplication53.Entity.MoviesDirectors;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesDirectorsRepository extends JpaRepository<MoviesDirectors, Integer> {
    @Modifying
    @Transactional
    @Query("delete MoviesDirectors m where m.movie.id = ?1")
    void deleteMoviesActorsByMovie(Integer movieId);
}

