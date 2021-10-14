package hibernates;

import domein.Adres;
import domein.OVChipkaart;
import domein.Product;
import interfaces.ProductDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private final Session session;

    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product product) {
        Transaction transaction = this.session.beginTransaction();
        try {
            session.save(product);
            transaction.commit();
            return true;
        } catch (Exception exception){
            exception.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        try {
            Transaction transaction = this.session.beginTransaction();
            session.update(product);
            transaction.commit();
            return true;
        } catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        try {
            Transaction transaction = this.session.beginTransaction();
            session.delete(product);
            transaction.commit();
            return true;
        } catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            String query = "FROM Product";
            List<Product> producten = session.createQuery(query, Product.class).list();
            // Nieuwe lijst om producten op te slaan die op een ov chipkaart staan
            List<Product> ovChipkaartProducten = new ArrayList<>();

            for (Product product :  producten){
                if (product.getOvChipkaarten().contains(ovChipkaart)) {
                    ovChipkaartProducten.add(product);
                }
            }
            return ovChipkaartProducten;
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            String query = "FROM Product";
            List<Product> producten = session.createQuery(query, Product.class).list();
            return producten;
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }
}
