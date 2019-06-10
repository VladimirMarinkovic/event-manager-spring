package vlada.springframework.eventmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vlada.springframework.eventmanager.model.Korisnik;
import vlada.springframework.eventmanager.model.Zadatak;
import vlada.springframework.eventmanager.repository.ZadatakRepository;
import vlada.springframework.eventmanager.services.KorisnikService;
import vlada.springframework.eventmanager.services.ZadatakService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ZadatakController {

    @Autowired
    private ZadatakService zadatakService;
    @Autowired
    private ZadatakRepository zadatakRepository;
    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/dodajZadatak")
    public String zadatakForm(String email, Model model, HttpSession session) {
        session.setAttribute("email", email);
        model.addAttribute("zadatak", new Zadatak());
        return "views/zadatakForm";
    }

    @PostMapping("/dodajZadatak")
    public String dodajZadatak(@Valid Zadatak zadatak, BindingResult bindingResult, HttpSession session) {
        if(bindingResult.hasErrors()) {
            return"views/zadatakForm";
        }
        String email = (String) session.getAttribute("email");
        zadatakService.dodajZadatak(zadatak, korisnikService.findOne(email));

        return"redirect:/korisnici";
    }





    /*
    @GetMapping( value = "/prikazi_zadatak/{id}")
    public String prikaziZadatak(@PathVariable Long id,Model model,Principal principal) {
        Zadatak zadatak = zadatakService.findById(id);
        if(zadatakService.isKorisnikovZadatak(principal,zadatak)){
        model.addAttribute("zadatak", zadatak);
        return "views/zadatakPrikazi";
        }else {
            model.addAttribute("poruka_nije_korisnikov", true);
            return "views/error_login";
        }
    }
    */

    @RequestMapping(value="/prikazi_zadatak/{id}", method = RequestMethod.GET)
    public ModelAndView modelAndView(@PathVariable Long id,ModelAndView modelAndView,Principal principal) {
        Zadatak zadatak = zadatakService.findById(id);
        if (zadatak != null) {
            if (zadatakService.isKorisnikovZadatak(principal, zadatak)) {
                modelAndView.addObject("zadatak", zadatak);
                modelAndView.setViewName("views/zadatakPrikazi");
            }
        } else {
            modelAndView.addObject("poruka_nije_korisnikov", true);
            modelAndView.setViewName("views/error_login");
        }

            return modelAndView;
        }


        /* // Svi Zadaci
    @RequestMapping(value="/sviZadaci", method = RequestMethod.GET)
    public ModelAndView modelAndView(ModelAndView modelAndView) {
        modelAndView.addObject("zadaci",zadatakService.sviZadaci());
        modelAndView.setViewName("views/zadatakSviZadaci");
        return modelAndView;
    }
    */


    @GetMapping("/sviZadaci")
    public String listaKorisnika(Model model, @RequestParam(defaultValue="")  String naslov) {
        model.addAttribute("zadaci", zadatakService.findByNaslov(naslov));
        return "views/zadatakSviZadaci";
    }





    }
