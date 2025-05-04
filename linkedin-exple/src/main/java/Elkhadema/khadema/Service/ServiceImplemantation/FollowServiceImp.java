package Elkhadema.khadema.Service.ServiceImplemantation;

import java.util.List;
import java.util.stream.Collectors;

import Elkhadema.khadema.DAO.DAOImplemantation.FollowDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.UserDAO;
import Elkhadema.khadema.Service.ServiceInterfaces.FollowService;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.domain.Follow;

public class FollowServiceImp implements FollowService {
    FollowDAO followDAO = new FollowDAO();
    UserDAO userDAO=new UserDAO();
    @Override
    public List<User> getFollowers(User user) {
        return followDAO.getAllFollowersById(user.getId()).stream().map(Follow::getFollowing).collect(Collectors.toList());
    }

    @Override
    public List<User> getfollowing(User user) {
        return followDAO.getfollowingByid(user.getId()).stream().map(Follow::getFollower).collect(Collectors.toList());
    }

    @Override
    public boolean isFollowing(User user, User following) {
        List<User> s= getfollowing(following);
        return  s.stream().anyMatch(t -> user.getId()==t.getId());
    }

    @Override
    public void Follow(User user, User following) {
        followDAO.save(new Follow(user, following));
    }

    @Override
    public void unFollow(User user, User following) {
        followDAO.delete(new Follow(user, following));
    }

}
