package domein;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ov_chipkaart")
public class OVChipkaart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private double saldo;

    @ManyToOne
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    @ManyToMany(mappedBy = "ovChipkaarten")
    private List<Product> producten = new ArrayList<>();

    public OVChipkaart(Date geldig_tot, int klasse, double saldo) {
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
    }

    public OVChipkaart() {
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Product> getProducten() {
        return producten;
    }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public void productToevoegen(Product pro) {
        producten.add(pro);
    }

    @Override
    public String toString() {
        return "OVChipkaart: #" + kaart_nummer + " geldig tot: " + geldig_tot +  ", klasse " + klasse + ", saldo = " + saldo + ", reiziger id = " + reiziger.getId();
    }
}
