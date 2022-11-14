package soen387.DAO;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    void create(T t) throws ClassNotFoundException;

    T get(long id) throws ClassNotFoundException;

    T get(String idStr) throws ClassNotFoundException;

    List<T> getAll() throws ClassNotFoundException;

    void update(T t, String[] params);

    void delete(T t);
}
