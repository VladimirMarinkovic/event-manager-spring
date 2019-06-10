package vlada.springframework.eventmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vlada.springframework.eventmanager.model.Korisnik;
import vlada.springframework.eventmanager.repository.KorisnikRepository;
import vlada.springframework.eventmanager.services.KorisnikService;
import vlada.springframework.eventmanager.services.ZadatakService;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private ZadatakService zadatakService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    KorisnikRepository korisnikRepository;


    @GetMapping("/profile")
    public String prikaziProfileStranicu(Model model, Principal principal) {

        String email = principal.getName();
        Korisnik korisnik = korisnikService.findOne(email);
       if(korisnik.isAktivan() == false) {
            model.addAttribute("neaktivan",true);
            model.addAttribute("email", korisnik.getEmail() + " ");
             return "views/error_login";
        }

        model.addAttribute("zadaci", zadatakService.findKorisnikZadatak(korisnik));
        model.addAttribute("userName", korisnik.getIme() + " " + korisnik.getPrezime() + " (" + korisnik.getEmail() + ")");

        return "views/profile";
    }



}
