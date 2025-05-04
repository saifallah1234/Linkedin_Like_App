package Elkhadema.khadema.DAO.DAOInterfaces;

import java.util.List;

import Elkhadema.khadema.domain.Follow;

public interface FollowerDAOINT extends Dao<Follow> {
	public List<Follow> getfollowersByuserid(long id);

	public List<Follow> getfollowingByuserid(long id);

}
