package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error/")
public class ErrorController {

    @RequestMapping("")
    public String errorPage(Model model) {
        return "error";
    }
}
