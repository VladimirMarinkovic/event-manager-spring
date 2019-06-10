package vlada.springframework.eventmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RegistracijaToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private long tokenId;

    @Column(name = "registracija_token")
    private String registracijaToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date vremeKreiranja;

    @OneToOne(targetEntity = Korisnik.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "email")
    private Korisnik korisnik;

    public RegistracijaToken(Korisnik korisnik) {
        this.korisnik = korisnik;
        vremeKreiranja = new Date();
        registracijaToken = UUID.randomUUID().toString();
    }
}
