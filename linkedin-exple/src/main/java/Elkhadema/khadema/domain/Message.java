package Elkhadema.khadema.domain;

import java.util.Date;

public class Message {
    private long id;
    private User sender;
    private String content;
    private Date creationDate;
    private int parentMessageId;
    private int read;
    private Media image=null;
    public int getRead(){
        return read;
    }
    public void setRead(int read){
        this.read=read;
    }


    public Message(long id, User sender, String content, Date creationDate, int parentMessageId) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.creationDate = creationDate;
        this.parentMessageId = parentMessageId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(int parentMessageId) {
        this.parentMessageId = parentMessageId;
    }
	public Media getImage() {
		return image;
	}
	public void setImage(Media image) {
		this.image = image;
	}

}
