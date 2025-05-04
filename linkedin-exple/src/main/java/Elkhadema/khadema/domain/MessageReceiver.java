package Elkhadema.khadema.domain;

public class MessageReceiver {
    private Message message;
    private User user;
    private int read;
    private int id;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MessageReceiver(Message message, User user, int read, int id) {
		super();
		this.message = message;
		this.user = user;
		this.read = read;
		this.id = id;
	}

	public MessageReceiver(Message message, User user, int read) {
        this.message = message;
        this.user = user;
        this.read = read;
        
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int isRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

}
