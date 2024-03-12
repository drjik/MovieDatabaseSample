package drjik.springapplication53.Entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "actors")
@Getter
@Setter
public class Actor {
    @Id
    private int id;
    private String name;
    private String lastname;

    @OneToMany(mappedBy = "actor")
    private List<MoviesActors> moviesActorsList;
}
