package hibernates;

import domein.OVChipkaart;
import domein.Reiziger;
import interfaces.OVChipkaartDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private final Session session;

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        Transaction transaction = this.session.beginTransaction();
        try {
            session.save(ovChipkaart);
            transaction.commit();
            return true;
        } catch (Exception exception){
            exception.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            Transaction transaction = this.session.beginTransaction();
            session.update(ovChipkaart);
            transaction.commit();
            return true;
        } catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            Transaction transaction = this.session.beginTransaction();
            session.delete(ovChipkaart);
            transaction.commit();
            return true;
        } catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            List<OVChipkaart> ovChipkaarten = session.createQuery("FROM ov_chipkaart WHERE reiziger_id = " + reiziger.getId(), OVChipkaart.class).list();
            return ovChipkaarten;
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }
}
