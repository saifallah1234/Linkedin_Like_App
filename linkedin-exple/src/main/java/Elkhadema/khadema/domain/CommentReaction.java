package Elkhadema.khadema.domain;

import java.util.Date;

@Deprecated
public class CommentReaction {

    private User user;
    private Comment comment;
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

    public CommentReaction(User user, Comment comment, String type, Date creationDate) {
        this.user = user;
        this.setComment(comment);
        this.type = type;
        this.creationDate = creationDate;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

}
