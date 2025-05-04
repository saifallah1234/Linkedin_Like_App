package Elkhadema.khadema.domain;

import java.util.Date;
import java.util.List;


public class Post {
	private long id;
    private User user;
    private String content;
    private List<Reaction> reactions;
    private long parentPostId;
    private String type;
    private Date CreationDate;
    private List<Media> postMedias;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public long getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(long parentPostId) {
        this.parentPostId = parentPostId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post(User user, String content, List<Reaction> reactions, long parentPostId, String type, Date creationDate,
            long id) {
        this.user = user;
        this.content = content;
        this.reactions = reactions;
        this.parentPostId = parentPostId;
        this.type = type;
        CreationDate = creationDate;
        this.id = id;
    }

	public Post(long id) {
		super();
		this.id = id;
	}

    public long getCountReactions() {
    	if(this.reactions==null) {
    		return 0;
    	}
        return this.reactions.stream().count();
    }

	public List<Media> getPostMedias() {
		return postMedias;
	}

	public void setPostMedias(List<Media> postMedias) {
		this.postMedias = postMedias;
	}


}
