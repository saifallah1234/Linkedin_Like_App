package Elkhadema.khadema.domain;

public class JobOffre {
    private long id;
    private Company company;
    private String summary;
    private String postion;
    private double payRange;
    private String employmentType;
    private String location;

    public JobOffre(long id, Company company, String summary, String postion, double payRange, String employmentType,
            String location) {
        this.id = id;
        this.company = company;
        this.summary = summary;
        this.postion = postion;
        this.payRange = payRange;
        this.employmentType = employmentType;
        this.location = location;
    }

    public JobOffre(long id) {
    	this.id=id;
    }

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public double getPayRange() {
        return payRange;
    }

    public void setPayRange(double payRange) {
        this.payRange = payRange;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "JobOffre [id=" + id + ", company=" + company + ", summary=" + summary + ", postion=" + postion
                + ", payRange=" + payRange + ", employmentType=" + employmentType + ", location=" + location + "]";
    }

}
