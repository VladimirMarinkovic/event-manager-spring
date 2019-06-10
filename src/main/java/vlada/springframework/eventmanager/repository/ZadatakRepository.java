package vlada.springframework.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlada.springframework.eventmanager.model.Korisnik;
import vlada.springframework.eventmanager.model.Zadatak;

import java.util.List;
import java.util.Optional;

public interface ZadatakRepository extends JpaRepository<Zadatak, Long> {

    List<Zadatak> findByKorisnik(Korisnik korisnik);

    List<Zadatak> findByNaslovLike(String naslov);
    List<Zadatak> findByNaslovLikeOrderByNaslovAsc(String naslov);





}
