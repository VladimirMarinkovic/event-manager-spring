package vlada.springframework.eventmanager.repository;

import org.springframework.data.repository.CrudRepository;
import vlada.springframework.eventmanager.model.RegistracijaToken;

public interface RegistracijaTokenRepository extends CrudRepository<RegistracijaToken, String> {
    RegistracijaToken findByRegistracijaToken(String registracijaToken);
}
