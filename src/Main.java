import domein.Adres;
import domein.OVChipkaart;
import domein.Product;
import domein.Reiziger;
import hibernates.AdresDAOHibernate;
import hibernates.OVChipkaartDAOHibernate;
import hibernates.ProductDAOHibernate;
import hibernates.ReizigerDAOHibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
        testDAOHibernate();
//        testFetchAll();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    public static void testDAOHibernate() {
        // Sessions maken
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(getSession());
        AdresDAOHibernate adao = new AdresDAOHibernate(getSession());
        OVChipkaartDAOHibernate odao = new OVChipkaartDAOHibernate(getSession());
        ProductDAOHibernate pdao = new ProductDAOHibernate(getSession());

        // Reiziger, Adres, OVChipkaart en Product maken
        Reiziger reiziger = new Reiziger("P", "", "Ooms", java.sql.Date.valueOf("1999-09-09"));
        Reiziger reiziger2 = new Reiziger("P", "", "Ooms", java.sql.Date.valueOf("1997-12-24"));
        Adres adres = new Adres("4208BJ", "90", "Ruigenhoek", "Gorinchem");
        Adres adres2 = new Adres("1234AB", "123", "ABC", "Gorinchem");
        OVChipkaart ovChipkaart = new OVChipkaart(java.sql.Date.valueOf("2031-10-12"), 3, 9999.99);
        Product product = new Product("TEST PRODUCT", "TEST beschrijving", 420.00);

        // Adres-reiziger setten
        adres.setReiziger(reiziger);
        adres2.setReiziger(reiziger2);

        // OVChipkaart setten
        ovChipkaart.setReiziger(reiziger);

        product.ovChipkaartToevoegen(ovChipkaart);

        System.out.println("\n-------------------- Test ReizigerDAO -----------------------");
        List<Reiziger> reizigers = rdao.findAll();

        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(reiziger);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        rdao.save(reiziger2);

        System.out.println("[Test] ReizigerDAO.update() heeft de gegevens aangepast en opgeslagen.");
        reiziger.setVoorletters("X");
        rdao.update(reiziger);

        System.out.println("[Test] ReizigerDAO.findById() heeft de volgende reiziger gevonden:");
        System.out.println(rdao.findById(reiziger.getId()));

        System.out.println("[Test] ReizigerDAO.findByGbdatum() heeft de volgende reiziger gevonden:");
        System.out.println(rdao.findByGbdatum("1999-09-09"));


        System.out.println("\n\n\n-------------------- Test AdresDAO -----------------------");
        List<Adres> adressen = adao.findAll();

        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(adres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");

        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        adao.save(adres2);

        System.out.println("[Test] AdresDAO.update() heeft de gegevens aangepast en opgeslagen.");
        adres.setPostcode("4210AB");
        adao.update(adres);

        System.out.println("[Test] AdresDAO.findByReiziger() heeft het volgende adres gevonden:");
        System.out.println(adao.findByReiziger(reiziger2));


        System.out.println("\n\n\n-------------------- Test ProductDAO -----------------------");
        List<Product> producten = pdao.findAll();

        System.out.print("[Test] Eerst " + producten.size() + " producten, na ProductDAO.save() ");
        pdao.save(product);
        producten = pdao.findAll();
        System.out.println(producten.size() + " producten\n");

        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product p : producten) {
            System.out.println(p);
        }
        System.out.println();

        System.out.println("[Test] ProductDAO.update() heeft de gegevens aangepast en opgeslagen.");
        product.setPrijs(1000.00);
        pdao.update(product);

        System.out.println("[Test] ProductDAO.findByOVChipkaart() heeft de volgende producten op deze OVChipkaart gevonden:");
        System.out.println(pdao.findByOVChipkaart(ovChipkaart));


        System.out.println("\n\n\n-------------------- Test OVChipkaartDAO -----------------------");
        System.out.print("[Test] OVChipkaartDAO.save() is uitgevoerd\n");
        odao.save(ovChipkaart);

        System.out.println("[Test] OVChipkaartDAO.findByReiziger() heeft de volgende OV Chipkaarten gevonden:");
        System.out.println(odao.findByReiziger(reiziger));

        System.out.println("[Test] OVChipkaartDAO.update() heeft de gegevens aangepast en opgeslagen.");
        ovChipkaart.setKlasse(2);
        odao.update(ovChipkaart);

        System.out.println("[Test] Product en OVChipkaart persisteren.");
        ovChipkaart.getProducten().add(product);
        odao.update(ovChipkaart);
        System.out.println(odao.findByReiziger(reiziger));


        System.out.println("\n\n\n-------------------- Test Delete functies -----------------------");
        System.out.println("[Test] OVChipkaartDAO.delete() heeft de reiziger verwijderd.");
        odao.delete(ovChipkaart);

        System.out.println("[Test] ProductDAO.delete() heeft de reiziger verwijderd.");
        pdao.delete(product);

        System.out.println("[Test] AdresDAO.delete() heeft de reiziger verwijderd.");
        adao.delete(adres);

        System.out.println("[Test] ReizigerDAO.delete() heeft de reiziger verwijderd.");
        rdao.delete(reiziger);


    }
}