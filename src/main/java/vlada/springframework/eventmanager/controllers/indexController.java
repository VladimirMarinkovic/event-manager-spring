package vlada.springframework.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    @GetMapping({"/","/login"})
    public String prikaziIndexStranicu() {

        return "login";
    }





}
