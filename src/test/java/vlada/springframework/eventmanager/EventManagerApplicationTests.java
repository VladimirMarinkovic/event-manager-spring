package vlada.springframework.eventmanager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import vlada.springframework.eventmanager.model.Korisnik;
import vlada.springframework.eventmanager.model.Zadatak;
import vlada.springframework.eventmanager.services.KorisnikService;
import vlada.springframework.eventmanager.services.ZadatakService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventManagerApplicationTests {

    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private ZadatakService zadatakService;

    @Before
    public void initDb() {
        {
            Korisnik newKorisnik = new Korisnik("testKorisnik@mail.com", "testKorisnikIme","testKorisnikPrezime", "123456");
            korisnikService.kreirajKorisnika(newKorisnik);
        }
        {
            Korisnik newKorisnik = new Korisnik("testAdmin@mail.com", "testAdminIme","testAdminPrezime", "123456");
            korisnikService.kreirajKorisnika(newKorisnik);
        }

        Zadatak korisnikZadatak = new Zadatak("03/01/2018", "00:11", "11:00", "Neki zadatak");
        Korisnik korisnik = korisnikService.findOne("testKorisnik@mail.com");
        zadatakService.dodajZadatak(korisnikZadatak, korisnik);
    }

    @Test
    public void testKorisnik() {
        Korisnik korisnik = korisnikService.findOne("testKorisnik@mail.com");
        assertNotNull(korisnik);
        Korisnik admin = korisnikService.findOne("testAdmin@mail.com");
        assertEquals(admin.getEmail(),"testAdmin@mail.com");
    }

    @Test
    public void testZadatak() {
        Korisnik korisnik = korisnikService.findOne("testKorisnik@mail.com");
        List<Zadatak> zadaci = zadatakService.findKorisnikZadatak(korisnik);
        assertNotNull(zadaci);

    }

}
