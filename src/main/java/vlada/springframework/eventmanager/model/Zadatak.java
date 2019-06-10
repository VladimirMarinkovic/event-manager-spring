package vlada.springframework.eventmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Zadatak {

    @Id
    @GeneratedValue
    private Long id;
    @Column(length=191)
    private String naslov;
    @NotEmpty
    @DateTimeFormat(pattern="dd-MMM-YYYY")
    private String datum;
    @NotEmpty
    private String vremePocetka;
    @NotEmpty
    private String vremeZavrsetka;
    @NotEmpty
    @Column(length=2000)
    private String opisZadatka;
    @ManyToOne
    @JoinColumn(name="email")
    private Korisnik korisnik;

    //obrisati nakon dodavanja naslova
    public Zadatak(@NotEmpty String datum, @NotEmpty String vremePocetka, @NotEmpty String vremeZavrsetka, @NotEmpty String opisZadatka) {
        this.datum = datum;
        this.vremePocetka = vremePocetka;
        this.vremeZavrsetka = vremeZavrsetka;
        this.opisZadatka = opisZadatka;
    }

    public Zadatak(String naslov, @NotEmpty String datum, @NotEmpty String vremePocetka, @NotEmpty String vremeZavrsetka, @NotEmpty String opisZadatka) {
        this.naslov = naslov;
        this.datum = datum;
        this.vremePocetka = vremePocetka;
        this.vremeZavrsetka = vremeZavrsetka;
        this.opisZadatka = opisZadatka;
    }
}
