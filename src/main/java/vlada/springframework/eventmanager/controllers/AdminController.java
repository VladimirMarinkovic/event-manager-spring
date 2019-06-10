package vlada.springframework.eventmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vlada.springframework.eventmanager.model.Korisnik;
import vlada.springframework.eventmanager.services.KorisnikService;

import javax.validation.Valid;

@Controller
public class AdminController {


    @Autowired
    KorisnikService korisnikService;

    @RequestMapping(value="/kreirajAdmina", method = RequestMethod.GET)
    public String kreirajAdmina(Model model) {
        model.addAttribute("admin", new Korisnik());
        return "admin/kreirajAdmina";
    }

    @PostMapping("/kreirajAdmina")
    public String kreirajAdmina(@Valid Korisnik korisnik, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "admin/kreirajAdmina";
        }
        if(korisnikService.isUserPresent(korisnik.getEmail())) {
            model.addAttribute("postoji",true);

            return "admin/kreirajAdmina";
        }
        korisnikService.kreirajAdmina(korisnik);
        model.addAttribute("uspesnoAdmin",true);
        model.addAttribute("korisnik",korisnik);
        model.addAttribute("ime",korisnik.getIme());
        model.addAttribute("prezime",korisnik.getPrezime());
        return "views/uspesno_admin";

    }



    @RequestMapping(value="/kreirajRukovodioca", method = RequestMethod.GET)
    public String kreirajRukovodioca(Model model) {
        model.addAttribute("rukovodilac", new Korisnik());
        return "admin/kreirajRukovodioca";
    }

    @PostMapping("/kreirajRukovodioca")
    public String kreirajRukovodioca(@Valid Korisnik korisnik, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "admin/kreirajRukovodioca";
        }
        if(korisnikService.isUserPresent(korisnik.getEmail())) {
            model.addAttribute("postoji",true);

            return "admin/kreirajRukovodioca";
        }
        korisnikService.kreirajRukovodioca(korisnik);
        model.addAttribute("uspesnoRukovodioc",true);
        model.addAttribute("imeR",korisnik.getIme());
        model.addAttribute("prezimeR",korisnik.getPrezime());
        return "views/uspesno_admin";
    }

    @GetMapping("/korisnici")
    public String listaKorisnika(Model model, @RequestParam(defaultValue="")  String ime) {
        model.addAttribute("korisnici", korisnikService.findByIme(ime));
        return "admin/listaKorisnika";
    }

    /*  // Svi Korisnici
    @GetMapping("/korisnici")
    public String listaKorisnika(Model model) {
        model.addAttribute("korisnici", korisnikService.findAll());
        return "views/lista";
    }
    */



}
