package vlada.springframework.eventmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vlada.springframework.eventmanager.model.Korisnik;
import vlada.springframework.eventmanager.model.RegistracijaToken;
import vlada.springframework.eventmanager.repository.KorisnikRepository;
import vlada.springframework.eventmanager.repository.RegistracijaTokenRepository;
import vlada.springframework.eventmanager.services.EmailSenderService;
import vlada.springframework.eventmanager.services.KorisnikService;

@Controller
public class ResetLozinkaController {

    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    RegistracijaTokenRepository registracijaTokenRepository;
    @Autowired
    KorisnikRepository korisnikRepository;
    @Autowired
    EmailSenderService emailSenderService;


    @RequestMapping(value = "/zaboravljena_lozinka", method = RequestMethod.GET)
    public ModelAndView prikaziPasswordReset(ModelAndView modelAndView, Korisnik korisnik) {
        modelAndView.addObject("korisnik",korisnik);
        modelAndView.setViewName("zaboravljena_lozinka");
        return modelAndView;
    }

    @RequestMapping(value = "/zaboravljena_lozinka", method = RequestMethod.POST)
    public ModelAndView zaboravljenaKorisnikLozinka(ModelAndView modelAndView, Korisnik korisnik) {
        Korisnik regKorisnik = korisnikRepository.findByEmail(korisnik.getEmail());
        if(regKorisnik != null) {
            RegistracijaToken registracijaToken = new RegistracijaToken(regKorisnik);
            registracijaTokenRepository.save(registracijaToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(regKorisnik.getEmail());
            mailMessage.setSubject("Resetovanje Lozinke!");
            mailMessage.setFrom("exxperiment899@gmail.com");
            mailMessage.setText("Za zavrsetak procesa reseta lozinke,kliknite na link:  " +
                    "http://localhost:8082/reset_potvrdjen?token="+registracijaToken.getRegistracijaToken());

            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("poruka", true);
            modelAndView.addObject("email",korisnik.getEmail());
            modelAndView.setViewName("views/zaboravljena_lozinka_uspesno");
        }else {

            modelAndView.addObject("poruka_reset", true);
            modelAndView.addObject("email",korisnik.getEmail());
            modelAndView.setViewName("views/error_reset");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/reset_potvrdjen", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView proveriResetToken(ModelAndView modelAndView, @RequestParam("token")String confirmationToken){

        RegistracijaToken token = registracijaTokenRepository.findByRegistracijaToken(confirmationToken);

        if(token != null){
            Korisnik korisnik = korisnikRepository.findByEmail(token.getKorisnik().getEmail());
            korisnik.setAktivan(true);
            korisnikRepository.save(korisnik);
            modelAndView.addObject("korisnik",korisnik);
            modelAndView.addObject("email", korisnik.getEmail());
            modelAndView.setViewName("views/resetLozinka");
        }else {
            modelAndView.addObject("poruka_reset_potvrda", true);
            modelAndView.setViewName("views/error_reset");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/reset_lozinka", method = RequestMethod.POST)
    public ModelAndView resetujLozinku(ModelAndView modelAndView, Korisnik korisnik){

        if(korisnik.getEmail() != null) {
            Korisnik tokenKorisnik = korisnikRepository.findByEmail(korisnik.getEmail());
            BCryptPasswordEncoder encoder = new  BCryptPasswordEncoder();
            tokenKorisnik.setPassword(encoder.encode(korisnik.getPassword()));
            tokenKorisnik.setConfirmPassword(encoder.encode(korisnik.getConfirmPassword()));

            if(!korisnik.getPassword().equals(korisnik.getConfirmPassword())){
                modelAndView.addObject("razlicitaLozinka",true);
                modelAndView.setViewName("views/resetLozinka");
                return modelAndView;
            }

            korisnikRepository.save(tokenKorisnik);
            modelAndView.addObject("poruka_resetovano", true);
            modelAndView.addObject("korisnik",tokenKorisnik.getIme() + " " + tokenKorisnik.getPrezime());
            modelAndView.setViewName("views/zaboravljena_lozinka_uspesno");

        }else {
            modelAndView.addObject("poruka_reset_potvrda", true);
            modelAndView.setViewName("views/error_reset");
        }
        return modelAndView;
    }

}
