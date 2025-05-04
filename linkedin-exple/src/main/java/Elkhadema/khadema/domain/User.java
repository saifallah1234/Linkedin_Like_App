package Elkhadema.khadema.domain;

import java.util.Date;

public class User {
	private int id;
	private String password;
	private ContactInfo contactInfo;
	private String userName;
	private Date creationDate;
	private Date lastloginDate;
	private Media photo;
	public boolean is_banned = false;
	public boolean is_active = false;

	

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastloginDate() {
		return lastloginDate;
	}

	public void setLastloginDate(Date lastloginDate) {
		this.lastloginDate = lastloginDate;
	}

	public Media getPhoto() {
		return photo;
	}

	public void setPhoto(Media photo) {
		this.photo = photo;
	}

	public boolean isIs_banned() {
		return is_banned;
	}

	public void setIs_banned(boolean is_banned) {
		this.is_banned = is_banned;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public User(int id, String password, ContactInfo contactInfo, String userName, Date creationDate,
			Date lastloginDate, Media photo, boolean is_banned, boolean is_active) {
		this.id = id;
		this.password = password;
		this.contactInfo = contactInfo;
		this.userName = userName;
		this.creationDate = creationDate;
		this.lastloginDate = lastloginDate;
		this.photo = photo;
		this.is_banned = is_banned;
		this.is_active = is_active;
	}

	public User(int id, String password, String userName) {
		this.id = id;
		this.password = password;
		this.userName = userName;
	}

}
