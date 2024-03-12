package drjik.springapplication53.Repository;

import drjik.springapplication53.Entity.Movie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>, JpaSpecificationExecutor<Movie> {
    @Query("select m from Movie m order by m.rating desc limit 10")
    List<Movie> getMoviesByTopTen();

    @Modifying
    @Transactional
    @Query("delete Movie m where m.id = ?1")
    void deleteMoviesActorsByMovie(Integer id);
}

