package vlada.springframework.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlada.springframework.eventmanager.model.Korisnik;
import vlada.springframework.eventmanager.model.TipKorisnika;

public interface TipKorisnikaRepository extends JpaRepository<TipKorisnika, String> {
    String findByNaziv(String naziv);
}