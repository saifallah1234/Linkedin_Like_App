package Elkhadema.khadema.domain;

import java.util.Date;

public class Company extends User {
    private String companyName;
    private String description;
    private String industry;
    private String website;
    private int comapnySize;
    private String address;
    private String moto;
    private String speciality;

    public Company(int id, String password, String userName) {
        super(id, password, userName);
    }

    public Company(int id, String password, ContactInfo contactInfo, String userName, Date creationDate,
            Date lastloginDate, Media photo, boolean is_banned, boolean is_active, String companyName,
            String description, String industry, String website, int comapnySize, String address, String speciality) {
        super(id, password, contactInfo, userName, creationDate, lastloginDate, photo, is_banned, is_active);
        this.companyName = companyName;
        this.description = description;
        this.industry = industry;
        this.website = website;
        this.comapnySize = comapnySize;
        this.address = address;
        this.speciality = speciality;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getComapnySize() {
        return comapnySize;
    }

    public void setComapnySize(int comapnySize) {
        this.comapnySize = comapnySize;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMoto() {
        return moto;
    }

    public void setMoto(String moto) {
        this.moto = moto;
    }

}
