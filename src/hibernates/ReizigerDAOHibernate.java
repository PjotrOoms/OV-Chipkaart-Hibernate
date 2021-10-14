package hibernates;

import domein.Reiziger;
import interfaces.ReizigerDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOHibernate  implements ReizigerDAO {
    private final Session session;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        // transaction buiten de try zodat er een rollback kan plaats vinden als er iets fout gaat met het aanmaken van een object
        Transaction transaction = this.session.beginTransaction();
        try {
            session.save(reiziger);
            transaction.commit();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            transaction.rollback();
            return false;
        }

    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            Transaction transaction = this.session.beginTransaction();
            session.update(reiziger);
            transaction.commit();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            Transaction transaction = this.session.beginTransaction();
            session.delete(reiziger);
            transaction.commit();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public Reiziger findById(Long id) {
        try {
            Reiziger reiziger = session.get(Reiziger.class, id);
            return reiziger;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            String query = "FROM Reiziger WHERE geboortedatum = '" + datum + "'";
            List<Reiziger> reizigers = session.createQuery(query, Reiziger.class).list();
            return reizigers;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            String query = "FROM Reiziger";
            List<Reiziger> reizigers = session.createQuery(query, Reiziger.class).list();
            return reizigers;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
