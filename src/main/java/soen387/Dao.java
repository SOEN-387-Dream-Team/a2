package soen387;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    void create(T t);

    Optional<T> get(long id);

    Optional<T> get(String idStr);

    List<T> getAll();

    void update(T t, String[] params);

    void delete(T t);
}
