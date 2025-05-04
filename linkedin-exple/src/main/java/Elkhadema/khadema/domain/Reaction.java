package Elkhadema.khadema.domain;

import java.util.Date;

public class Reaction {
    private User user;
    private Post post;
    private String type;
    private Date creationDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Reaction(User user, Post post, String type, Date creationDate) {
        this.user = user;
        this.post = post;
        this.type = type;
        this.creationDate = creationDate;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
