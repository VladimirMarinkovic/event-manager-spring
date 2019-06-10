package vlada.springframework.eventmanager.model;


import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Korisnik {


    @Id
    @Column(unique = true, name = "email")
    @Email(message = "*Unesite emial")
    @NotEmpty(message = "*Molimo vas unesite email")
    private String email;

    @Column(name = "ime")
    @NotEmpty(message = "*Unesite ime")
    private String ime;

    @Column(name = "prezime")
    @NotEmpty(message = "*Unesite prezime")
    private String prezime;

    @Column(name = "password")
    @Length(min = 5, message = "*Lozinka mora imati najmanje 5 karaktera")
    @NotEmpty(message = "*Milomo vas unesite lozinku")
    private String password;

    @Column(name = "confirmPassword")
    @Length(min = 5, message = "*Ponovite Lozinku")
    // @NotEmpty(message = "*Milomo vas ponovite lozinku")
    private String confirmPassword;

    @Column(nullable = true,name = "telefon")
    private String telefon;

    @DateTimeFormat(pattern="dd-MMM-YYYY")
    @Column(nullable = true,name = "datumRodjenja")
    private String datumRodjenja;


    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    private List<Zadatak> zadaci;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "korisnik_tip_korisnika", joinColumns = @JoinColumn(name = "email"),
            inverseJoinColumns = @JoinColumn(name = "naziv"))
    private List<TipKorisnika> tipoviKorisnika;

    //dodao
    @Column(name = "aktivan")
    private boolean aktivan;


    public Korisnik(@Email(message = "*Unesite emial") @NotEmpty(message = "*Molimo vas unesite email") String email, @NotEmpty(message = "*Unesite ime") String ime, @NotEmpty(message = "*Unesite prezime") String prezime, @Length(min = 5, message = "*Lozinka mora imati najmanje 5 karaktera") @NotEmpty(message = "*Milomo vas unesite plozinku") String password) {
        this.email = email;
        this.ime = ime;
        this.prezime = prezime;
        this.password = password;
        this.aktivan = false;
    }

    public Korisnik(@Email(message = "*Unesite emial") @NotEmpty(message = "*Molimo vas unesite email") String email, @NotEmpty(message = "*Unesite ime") String ime, @NotEmpty(message = "*Unesite prezime") String prezime, @Length(min = 5, message = "*Lozinka mora imati najmanje 5 karaktera") @NotEmpty(message = "*Milomo vas unesite lozinku") String password, @Length(min = 5) @NotEmpty(message = "*Milomo vas ponovite lozinku") String confirmPassword) {
        this.email = email;
        this.ime = ime;
        this.prezime = prezime;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.aktivan = false;
    }
}
