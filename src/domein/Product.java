package domein;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    @ManyToMany(mappedBy = "producten", targetEntity = OVChipkaart.class)
    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

    public Product(String naam, String beschrijving, double prijs) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product() {

    }

    public Long getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(Long product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public void ovChipkaartToevoegen(OVChipkaart ovc) {
        ovChipkaarten.add(ovc);
    }

    @Override
    public String toString() {
        return "Product: #" + product_nummer + " naam: " + naam + ", beschrijving: " + beschrijving + ", prijs = " + prijs + "\n";
    }
}
