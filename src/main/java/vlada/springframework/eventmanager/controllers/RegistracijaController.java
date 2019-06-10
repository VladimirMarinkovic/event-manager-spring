package vlada.springframework.eventmanager.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vlada.springframework.eventmanager.model.Korisnik;
import vlada.springframework.eventmanager.model.RegistracijaToken;
import vlada.springframework.eventmanager.repository.KorisnikRepository;
import vlada.springframework.eventmanager.repository.RegistracijaTokenRepository;
import vlada.springframework.eventmanager.services.EmailSenderService;
import vlada.springframework.eventmanager.services.KorisnikService;

import javax.validation.Valid;

@Getter
@Setter
@Controller
public class RegistracijaController {

    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private RegistracijaTokenRepository registracijaTokenRepository;
    @Autowired
    EmailSenderService emailSenderService;

    @GetMapping("/registracija")
    public String registracijaForm(Model model) {

        model.addAttribute("korisnik", new Korisnik());
        return "registracija";
    }



    @RequestMapping(value="/registracija", method = RequestMethod.POST)
    public String registerUser(@Valid Korisnik korisnik, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "registracija";
        }
        if(korisnikService.isUserPresent(korisnik.getEmail())) {
            model.addAttribute("exist",true);

            return "registracija";
        }
        if(!korisnik.getPassword().equals(korisnik.getConfirmPassword())){
            model.addAttribute("razlicitaLozinka",true);
            return "registracija";
        }
        else {
            korisnikService.kreirajKorisnika(korisnik);

            RegistracijaToken registracijaToken = new RegistracijaToken(korisnik);
            registracijaTokenRepository.save(registracijaToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(korisnik.getEmail());
            mailMessage.setSubject("Uspesno ste se registrovali!");
            mailMessage.setFrom("exxperiment899@gmail.com");
            mailMessage.setText("Da bi ste potvrdili vas nalog,molimo vas kliknite na link ispod:" +
                    "http://localhost:8082/potvrdi_nalog?token="+registracijaToken.getRegistracijaToken());
            emailSenderService.sendEmail(mailMessage);
            model.addAttribute("email", korisnik.getEmail());
        }
        return "views/uspesno";
    }



    @RequestMapping(value = "/potvrdi_nalog", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView potvrdiNalog(ModelAndView modelAndView, @RequestParam("token")String registracijaToken) {

        RegistracijaToken token = registracijaTokenRepository.findByRegistracijaToken(registracijaToken);
        if(token != null) {
            Korisnik korisnik = korisnikRepository.findByEmail(token.getKorisnik().getEmail());
            korisnik.setAktivan(true);
            korisnikRepository.save(korisnik);
            modelAndView.setViewName("views/nalog_potvrdjen");
        }
        else {
            modelAndView.addObject("poruka", "Link je ne vazeci!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }




}
