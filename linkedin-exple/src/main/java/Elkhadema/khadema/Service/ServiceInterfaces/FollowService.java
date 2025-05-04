package Elkhadema.khadema.Service.ServiceInterfaces;

import java.util.List;

import Elkhadema.khadema.domain.User;

public interface FollowService {
    List<User> getFollowers(User user);

    List<User> getfollowing(User user);

    boolean isFollowing(User user, User following);

    void Follow(User user, User following);

    void unFollow(User user, User following);
}
