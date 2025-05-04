package Elkhadema.khadema.domain;

public class Follow {
    private User follower;
    private User following;

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }

    public Follow(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }

}
