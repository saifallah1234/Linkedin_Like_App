package Elkhadema.khadema.domain;

import java.time.LocalDate;

public class Notification {
    private String type;
    private int id;
    private String content;
    private LocalDate date;
    private User user;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Notification(String type, String string, User user, LocalDate date, long l) {
        this.type = type;
        this.id = (int)l;
        this.content = string;
        this.user = user;
        this.date = date;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
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
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

}
