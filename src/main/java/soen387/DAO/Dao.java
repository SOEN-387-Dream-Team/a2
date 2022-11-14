package soen387.DAO;

import java.util.List;

public interface Dao<T> {

    void create(T t) throws ClassNotFoundException;

    T get(int id) throws ClassNotFoundException;

    T get(String idStr) throws ClassNotFoundException;

    List<T> getAll() throws ClassNotFoundException;

    void update(T t, String[] params);

    void delete(T t) throws ClassNotFoundException;
}
