package drjik.springapplication53.Service;

import drjik.springapplication53.Entity.Actor;
import drjik.springapplication53.Repository.ActorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ActorService {
    private ActorRepository actorRepository;

    public Page<Actor> getActors(Integer numberPage) {
        Pageable pageable = PageRequest.of(numberPage, 4);

        return actorRepository.findAll(pageable);
    }
}
