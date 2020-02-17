package guru.springframework.sfgrecipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"","/"})
    String getIndexPage(Model model) {
        System.out.println("Getting index page... again and again and again");
        return "index";
    }
}
