package Elkhadema.khadema.domain;

import java.util.Date;

public class Admin extends User {
    /**
     * admin level can be used to create diffrent
     * types of admin i.e one who can only see statistiques
     * of the site or one who can remove posts and ban users
     */
    private int adminLevel;

    public Admin(int id, String password, ContactInfo contactInfo, String userName, Date creationDate,
            Date lastloginDate, Media photo, boolean is_banned, boolean is_active, int adminLevel) {
        super(id, password, contactInfo, userName, creationDate, lastloginDate, photo, is_banned, is_active);
        this.adminLevel = adminLevel;
    }

    public Admin(int id, String password, String userName) {
        super(id, password, userName);
    }

    public int getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(int adminLevel) {
        this.adminLevel = adminLevel;
    }

}
