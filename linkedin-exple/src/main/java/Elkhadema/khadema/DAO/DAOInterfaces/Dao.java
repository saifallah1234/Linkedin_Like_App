package Elkhadema.khadema.DAO.DAOInterfaces;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t, T newT);

    void delete(T t);
}
