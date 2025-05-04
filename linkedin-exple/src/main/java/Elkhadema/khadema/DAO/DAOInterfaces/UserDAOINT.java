package Elkhadema.khadema.DAO.DAOInterfaces;

import java.util.List;
import java.util.Optional;

import Elkhadema.khadema.domain.ContactInfo;
import Elkhadema.khadema.domain.User;

public interface UserDAOINT extends Dao<User> {

    Optional<User> get(long id);

    List<User> getAll();

    void save(User t);

    void update(User t, User newT);

    void updateContactinfo(User t, ContactInfo ci);

    void delete(User t);

}