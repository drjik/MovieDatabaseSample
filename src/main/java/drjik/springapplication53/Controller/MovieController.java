package drjik.springapplication53.Controller;

import drjik.springapplication53.DAO.MovieDao;
import drjik.springapplication53.DTO.MovieFilter;
import drjik.springapplication53.Entity.Movie;
import drjik.springapplication53.Service.ActorService;
import drjik.springapplication53.Service.DirectorService;
import drjik.springapplication53.Service.GenreService;
import drjik.springapplication53.Service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class MovieController {
    private MovieService movieService;
    private ActorService actorService;
    private DirectorService directorService;
    private GenreService genreService;

    @RequestMapping(path = "/")
    public String index(
            Model model, @RequestParam(name = "page", required = false) Integer page) {
        if (page == null) {
            page = 0;
        }
        model.addAttribute("filter", new MovieFilter());
        model.addAttribute("movies", movieService.getPagesMovie(page).getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", movieService.getPagesMovie(page).getTotalPages());
        return "index";
    }

    @RequestMapping(path = "/movie/{id}")
    public String movie(@PathVariable Integer id, Model model) {
        model.addAttribute("movie", movieService.getPageMovie(id));
        return "show_movie";
    }

    @RequestMapping(path = "/actors")
    public String actors(Model model, @RequestParam(name = "page", required = false) Integer page) {
        if (page == null) {
            page = 0;
        }

        model.addAttribute("actors", actorService.getActors(page).getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", actorService.getActors(page).getTotalPages());
        return "actors";
    }

    @RequestMapping(path = "/directors")
    public String directors(Model model, @RequestParam(name = "page", required = false) Integer page) {
        if (page == null) {
            page = 0;
        }

        model.addAttribute("actors", directorService.getDirectors(page).getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", directorService.getDirectors(page).getTotalPages());
        return "actors";
    }

    @RequestMapping(path = "/top10")
    public String topTen(Model model) {
        model.addAttribute("movies", movieService.getMoviesTopTen());

        return "top_ten";
    }

    @RequestMapping(path = "movie/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("movie", movieService.getPageMovie(id));
        model.addAttribute("genres", genreService.getGenres());

        return "edit_movie";
    }

    @PostMapping(path = "movie/edit/{id}")
    public String editActive(@PathVariable Integer id,
                             @RequestParam(name = "name", required = false) String name,
                             @RequestParam(name = "year", required = false) Integer year,
                             @RequestParam(name = "genre", required = false) Integer genreId,
                             @RequestParam(name = "rating", required = false) Double rating) {
        movieService.updateMovie(id, name, year, genreService.getGenreById(genreId), rating);

        return "redirect:/movie/" + id;
    }

    @RequestMapping(path = "movie/delete/{id}")
    public String delete(@PathVariable Integer id) {
        movieService.deleteMovie(id);

        return "redirect:/";
    }

    @RequestMapping("/movies/filter")
    public String filtered(MovieFilter filter, Model model) {
        model.addAttribute("movies", movieService.filterByCriteria(filter));
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPage", movieService.getPagesMovie(1).getTotalPages());

        model.addAttribute("filter", filter);
        return "index";
    }
}
