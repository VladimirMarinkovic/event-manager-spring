package vlada.springframework.eventmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TipKorisnika {


    @Id
    @Column(name = "naziv")
    private String naziv;

    @ManyToMany(mappedBy = "tipoviKorisnika")
    private List<Korisnik> korisnici;

    public TipKorisnika(String naziv) {
        this.naziv = naziv;
    }
}
