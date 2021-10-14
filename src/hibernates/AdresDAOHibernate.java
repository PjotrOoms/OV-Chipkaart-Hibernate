package hibernates;

import domein.Adres;
import domein.Reiziger;
import interfaces.AdresDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private final Session session;

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Adres adres) {
        Transaction transaction = this.session.beginTransaction();
        try {
            session.save(adres);
            transaction.commit();
            return true;
        } catch (Exception exception){
            exception.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            Transaction transaction = this.session.beginTransaction();
            session.update(adres);
            transaction.commit();
            return true;
        } catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            Transaction transaction = this.session.beginTransaction();
            session.delete(adres);
            transaction.commit();
            return true;
        } catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            Adres adres = session.createQuery("FROM Adres WHERE reiziger_id = " + reiziger.getId(), Adres.class).getSingleResult();
            return adres;
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        try {
            String query = "FROM Adres";
            List<Adres> adressen = session.createQuery(query, Adres.class).list();
            return adressen;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
