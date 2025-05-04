package Elkhadema.khadema.Service.ServiceInterfaces;

import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.Exception.UserNotFoundException;

public interface UserService {
	/**
	 * @param user user to add
	 * @param type either person or company
	 * @return the user if succesfuly added
	 * else null for aleardy user in db
	 */
	public User SignUp(User user, String type);

	/**
	 * @param name username of user
	 * @param password password to be encryoted later
	 * @return user if succesful
	 * @throws UserNotFoundException when the user is not in the DB
	 */
	public User Login(String name, String password) throws UserNotFoundException;

	public User getUserById(User user);

	public void removeUser(User u);

	/**
	 * @param user the old user
	 * @param newUser updated user info like password, email or username pls don't touch id
	 * @return user if changed
	 * @throws UserNotFoundException when the user is not in the DB
	 */
	public User EditUser(User user, User newUser) throws UserNotFoundException;
	public void logOut(User user);
}
