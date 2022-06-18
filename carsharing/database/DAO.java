package carsharing.database;

import java.sql.SQLException;
import java.util.List;


public interface DAO<T> {

    List<T> getAll() throws SQLException;

    void update(T element, T updatedElement) throws SQLException;

    void add(T element) throws SQLException;

    void remove(T element) throws SQLException;

}
