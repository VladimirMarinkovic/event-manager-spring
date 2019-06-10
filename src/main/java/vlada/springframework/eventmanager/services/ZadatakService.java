package vlada.springframework.eventmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vlada.springframework.eventmanager.model.Korisnik;
import vlada.springframework.eventmanager.model.Zadatak;
import vlada.springframework.eventmanager.repository.ZadatakRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service("zadatakService")
public class ZadatakService {

    @Autowired
    private ZadatakRepository zadatakRepository;

    public void dodajZadatak(Zadatak zadatak, Korisnik korisnik) {
        zadatak.setKorisnik(korisnik);
        zadatakRepository.save(zadatak);
    }

    public List<Zadatak> findKorisnikZadatak(Korisnik korisnik) {
        return zadatakRepository.findByKorisnik(korisnik);
    }

    public Zadatak findById(Long id) { return  zadatakRepository.getOne(id);}

    public boolean isKorisnikovZadatak(Principal principal, Zadatak zadatak) {
        return principal != null && principal.getName().equals(zadatak.getKorisnik().getEmail());
    }

    public List<Zadatak> sviZadaci() {
        return zadatakRepository.findAll();
    }

    public List<Zadatak> findByNaslov(String naslov) {
        return  zadatakRepository.findByNaslovLikeOrderByNaslovAsc("%"+naslov+"%");
    }




}
