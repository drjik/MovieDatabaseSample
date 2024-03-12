package drjik.springapplication53.Service;

import drjik.springapplication53.Entity.Actor;
import drjik.springapplication53.Entity.Director;
import drjik.springapplication53.Repository.DirectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DirectorService {
    private DirectorRepository directorRepository;

    public Page<Director> getDirectors(Integer numberPage) {
        Pageable pageable = PageRequest.of(numberPage, 4);

        return directorRepository.findAll(pageable);
    }
}
