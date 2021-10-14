package interfaces;

import domein.Reiziger;

import java.sql.Date;
import java.util.List;

public interface ReizigerDAO {
    boolean save(Reiziger reiziger);
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);
    Reiziger findById(Long id);
    List<Reiziger>findByGbdatum(String datum);
    List<Reiziger>findAll();
}
