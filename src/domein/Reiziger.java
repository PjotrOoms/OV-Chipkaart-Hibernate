package domein;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reiziger")
public class Reiziger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reiziger_id")
    private Long id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    @OneToOne(
            mappedBy = "reiziger",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Adres adres;

    @OneToMany(
            mappedBy = "reiziger",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OVChipkaart> ovChipkaart = new ArrayList<>();

    public Reiziger(String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger() {
    }

    public Long getId() {
        return id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Adres getAdres() {
        return adres;
    }

    public List<OVChipkaart> getOvChipkaart() {
        return ovChipkaart;
    }

    public void setOvChipkaart(List<OVChipkaart> ovChipkaart) {
        this.ovChipkaart = ovChipkaart;
    }

    public String toString() {
        return "Reiziger: #" + id +
                ", " + voorletters +
                ", " + tussenvoegsel +
                ", " + achternaam +
                ", " + geboortedatum +
                ", {" + adres + "}";
    }
}
