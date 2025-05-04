package Elkhadema.khadema.domain;

import java.util.List;

@Deprecated
public class Comment {
    private int id;
    private Post post;
    private String content;
    private String contenttype;
    private User user;
    private List<Reaction> reactions;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public Comment(int id, String content, Post post, User user, String contenttype, List<Reaction> reactions) {
        this.content = content;
        this.id = id;
        this.contenttype = contenttype;
        this.post = post;
        this.user = user;
        this.reactions = reactions;
    }

    public Comment(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    };

}
