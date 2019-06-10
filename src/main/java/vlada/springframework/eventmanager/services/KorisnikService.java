package vlada.springframework.eventmanager.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vlada.springframework.eventmanager.model.Korisnik;
import vlada.springframework.eventmanager.model.TipKorisnika;
import vlada.springframework.eventmanager.repository.KorisnikRepository;


@Service
public class KorisnikService {


    @Autowired
    private KorisnikRepository korisnikRepository;

    public void kreirajKorisnika(Korisnik korisnik) {
        BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
        korisnik.setPassword(encoder.encode(korisnik.getPassword()));
        korisnik.setConfirmPassword(encoder.encode(korisnik.getConfirmPassword()));
        TipKorisnika tipKorisnika = new TipKorisnika("KORISNIK");
        List<TipKorisnika> tipoviKorisnika = new ArrayList<>();
        tipoviKorisnika.add(tipKorisnika);
        korisnik.setTipoviKorisnika(tipoviKorisnika);
        korisnikRepository.save(korisnik);
    }

    public void kreirajAdmina(Korisnik admin) {
        BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
        admin.setPassword(encoder.encode(admin.getPassword()));
        admin.setConfirmPassword(encoder.encode(admin.getConfirmPassword()));
        admin.setAktivan(true);
        TipKorisnika tipKorisnika = new TipKorisnika("ADMIN");
        List<TipKorisnika> tipoviKorisnika = new ArrayList<>();
        tipoviKorisnika.add(tipKorisnika);
        admin.setTipoviKorisnika(tipoviKorisnika);
        korisnikRepository.save(admin);
    }

    public void kreirajRukovodioca(Korisnik rukovodilac) {
        BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
        rukovodilac.setPassword(encoder.encode(rukovodilac.getPassword()));
        rukovodilac.setConfirmPassword(encoder.encode(rukovodilac.getConfirmPassword()));
        rukovodilac.setAktivan(true);
        TipKorisnika tipKorisnika = new TipKorisnika("RUKOVODILAC");
        List<TipKorisnika> tipoviKorisnika = new ArrayList<>();
        tipoviKorisnika.add(tipKorisnika);
        rukovodilac.setTipoviKorisnika(tipoviKorisnika);
        korisnikRepository.save(rukovodilac);

    }




    public Korisnik findOne(String  email) {
        return korisnikRepository.findByEmail(email);
    }

    public boolean isUserPresent(String email) {
        Korisnik korisnik=korisnikRepository.findByEmail(email);
        if(korisnik != null)
            return true;

        return false;
    }

    public List<Korisnik> findAll() {
        return korisnikRepository.findAll();
    }

    public List<Korisnik> findByIme(String ime) {
        return  korisnikRepository.findByImeLikeOrderByImeAsc("%"+ime+"%");
    }
}
