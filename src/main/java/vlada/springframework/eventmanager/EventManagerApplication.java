package vlada.springframework.eventmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vlada.springframework.eventmanager.model.Korisnik;
import vlada.springframework.eventmanager.services.KorisnikService;


@SpringBootApplication
public class EventManagerApplication implements CommandLineRunner{


    @Autowired
    private KorisnikService korisnikService;

    public static void main(String[] args) {
        SpringApplication.run(EventManagerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        Korisnik Admin = new Korisnik("admin@mail.com", "AdminIme", "AdminPrezime", "123456", "123456");
        korisnikService.kreirajAdmina(Admin);

    }






}
