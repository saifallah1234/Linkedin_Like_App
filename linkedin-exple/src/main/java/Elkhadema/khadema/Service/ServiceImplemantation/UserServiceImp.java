package Elkhadema.khadema.Service.ServiceImplemantation;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import Elkhadema.khadema.DAO.DAOImplemantation.CompanyDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.ContactInfoDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.PersonDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.UserDAO;
import Elkhadema.khadema.Service.ServiceInterfaces.UserService;
import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.Person;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.PasswordEncryptor;
import Elkhadema.khadema.util.Session;
import Elkhadema.khadema.util.Exception.UserNotFoundException;

public class UserServiceImp implements UserService {
	UserDAO userDao = new UserDAO();
	PersonDAO personDao = new PersonDAO();
	CompanyDAO companyDao = new CompanyDAO();
	ContactInfoDAO contactInfoDAO=new ContactInfoDAO();
	

	
	public User SignUpCompany(Company company) {
		if (userDao.get(company.getId()).isPresent()) {
			return null;
		}
		company.setCreationDate(new Date());
		company.setLastloginDate(new Date());
		String encryptedPassword = PasswordEncryptor.encryptPassword(company.getUserName(), company.getPassword());
		company.setPassword(encryptedPassword);
		try {
			userDao.save(company);
		} catch (IOException e) {
			e.printStackTrace();
		}
		companyDao.save(company);
		return company;
	}
	public User SignUpPerson(Person person) {
		if (userDao.get(person.getId()).isPresent()) {
			return null;
		}
		person.setCreationDate(new Date());
		person.setLastloginDate(new Date());
		String encryptedPassword = PasswordEncryptor.encryptPassword(person.getUserName(), person.getPassword());
		person.setPassword(encryptedPassword);
		try {
			userDao.save(person);
		} catch (IOException e) {
			e.printStackTrace();
		}

		personDao.save(person);
		return person;

	}

	@Override
	public User Login(String name, String password) throws UserNotFoundException {
		Optional<User> user = userDao.Login(name,PasswordEncryptor.encryptPassword(name, password));
		System.out.println("houni");
		if (!user.isPresent()) {
			throw new UserNotFoundException();
		}
		User user2 = user.get();
		if (!PasswordEncryptor.verifyPassword(name, password, user2.getPassword()) || user2.isIs_banned()) {
			return null;
		}
		user2.setContactInfo(contactInfoDAO.get(user2.getContactInfo().getId()).get());
		user2.setIs_active(true);
		userDao.update(user2, user2);
		Session.setUser(user2);
		return user2;
	}
	@Override
	public User getUserById(User user){
		if (companyDao.get(user.getId()).isPresent()) {
			return companyDao.get(user.getId()).get();
		}
		return personDao.get(user.getId()).get();
	}

	@Override
	public void logOut(User user) {
		User user2 = userDao.get(user.getId()).get();
		user.setIs_active(false);
		user.setLastloginDate(new Date());
		userDao.update(user2, user);

	}

	@Override
	public void removeUser(User u) {
		userDao.delete(u);
	}

	@Override
	public User EditUser(User u, User newUser) throws UserNotFoundException {
		Optional<User> user = userDao.get(u.getId());
		if (!user.isPresent()) {
			throw new UserNotFoundException();
		}
		userDao.update(u, newUser);
		if (u instanceof Person) {
			personDao.update(((Person)u), ((Person)newUser));
		}
		return newUser;
	}

	@Override
	public User SignUp(User user, String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
