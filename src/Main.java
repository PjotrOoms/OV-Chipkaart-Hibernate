import domein.Adres;
import domein.OVChipkaart;
import domein.Product;
import domein.Reiziger;
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
        testReiziger_Adres();
        testReiziger_OVChipkaart();
        testOVChipkaart_Product();
        testFetchAll();
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

    private static void testReiziger_Adres() {
        Session session = getSession();

        Reiziger r = new Reiziger("P", null, "Ooms", Date.valueOf("1999-09-09"));
        Adres a = new Adres("4208BJ", "90", "Ruigenhoek", "Gorinchem");

        //Reiziger opslaan
        session.beginTransaction();
        session.save(r);
        session.getTransaction().commit();

        //Reiziger op adres setten
        a.setReiziger(r);

        //Adres opslaan
        session.beginTransaction();
        session.save(a);
        session.getTransaction().commit();

        //Sessie afsluiten
        session.close();
    }

    private static void testReiziger_OVChipkaart() {
        //Reiziger + ovchipkaarten aanmaken
        Reiziger r = new Reiziger("J", null, "Ooms", Date.valueOf("1997-12-24"));
        OVChipkaart o = new OVChipkaart(Date.valueOf("2025-01-01"), 2, 9999);
        OVChipkaart o2 = new OVChipkaart(Date.valueOf("2030-01-01"), 1, 8888);
        OVChipkaart o3 = new OVChipkaart(Date.valueOf("2035-01-01"), 2, 7777);

        //OVChipkaarten op de reiziger setten
        o.setReiziger(r);
        o2.setReiziger(r);
        o3.setReiziger(r);

        //Lijst v OV's op reiziger setten

        List<OVChipkaart> ovChipkaarten = Arrays.asList(o, o2, o3);
        r.setOvChipkaart(ovChipkaarten);

        Session session = getSession();

        //OVChipkaarten opslaan
        session.beginTransaction();
        session.save(r);
        session.getTransaction().commit();

        //Sessie afsluiten
        session.close();
    }

    private static void testOVChipkaart_Product() {
        // Reiziger + OV's + Producten aanmaken
        Reiziger r = new Reiziger("P", null, "Ooms", Date.valueOf("2001-10-30"));
        OVChipkaart o = new OVChipkaart(Date.valueOf("2022-01-01"), 2, 1234);
        OVChipkaart o2 = new OVChipkaart(Date.valueOf("2024-09-01"), 1, 5678);

        Product p = new Product("Weekkaart", "Een hele week reizen", 420.00);
        Product p2 = new Product("Maandkaart", "Een hele maand reizen", 9999.99);

        List<OVChipkaart> ovChipkaarten = Arrays.asList(o, o2);
        List<Product> producten = Arrays.asList(p, p2);

        o.setReiziger(r);
        o2.setReiziger(r);

        r.setOvChipkaart(ovChipkaarten);

        Session session = getSession();

        session.save(r);

        //OVChipkaarten opslaan
        for (OVChipkaart ovc : ovChipkaarten) {
            ovc.setReiziger(r);
            session.beginTransaction();
            session.save(o);
            session.getTransaction().commit();
        }

        //OVChipkaarten op product setten
        p.ovChipkaartToevoegen(o);
        p2.ovChipkaartToevoegen(o2);

        //Producten opslaan
        for (Product pro : producten) {
            session.beginTransaction();
            session.save(pro);
            session.getTransaction().commit();
        }

        session.close();
    }
}