package vlada.springframework.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vlada.springframework.eventmanager.model.Korisnik;

import javax.persistence.Id;
import java.util.List;

public interface KorisnikRepository extends JpaRepository<Korisnik, String> {

    Korisnik findByEmail(String email);
    List<Korisnik> findByImeLikeOrderByImeAsc(String ime);

}
