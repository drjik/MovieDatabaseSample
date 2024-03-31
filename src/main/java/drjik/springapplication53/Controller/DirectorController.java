package drjik.springapplication53.Controller;

import drjik.springapplication53.Service.DirectorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class DirectorController {
    private DirectorService directorService;

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
}
