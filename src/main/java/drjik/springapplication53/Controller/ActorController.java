package drjik.springapplication53.Controller;

import drjik.springapplication53.Service.ActorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ActorController {
    private ActorService actorService;
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
}
