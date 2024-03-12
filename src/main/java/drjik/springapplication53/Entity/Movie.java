package drjik.springapplication53.Entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int year;

    @ManyToOne
    @JoinColumn(name = "genres_id")
    private Genre genre;

    @OneToMany(mappedBy = "movie")
    private List<Comment>  comments;

    @OneToMany(mappedBy = "movie")
    private List<MoviesActors> moviesActors;

    @OneToMany(mappedBy = "movie")
    private List<MoviesDirectors> moviesDirectors;

    private Double rating;
}
