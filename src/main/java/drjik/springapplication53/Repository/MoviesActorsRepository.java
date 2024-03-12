package drjik.springapplication53.Repository;

import drjik.springapplication53.Entity.MoviesActors;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesActorsRepository extends JpaRepository<MoviesActors, Integer> {
    @Modifying
    @Transactional
    @Query("delete MoviesActors m where m.movie.id = ?1")
    void deleteMoviesActorsByMovie(Integer movieId);
}

