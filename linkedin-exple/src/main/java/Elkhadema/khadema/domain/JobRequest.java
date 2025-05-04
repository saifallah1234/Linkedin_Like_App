package Elkhadema.khadema.domain;


public class JobRequest {
    private User user;
    private JobOffre jobOffre;
    private byte[] pdf;
    private String etat;

    public JobRequest(User user, JobOffre jobOffre, String etat) {
        this.user = user;
        this.jobOffre = jobOffre;
        this.etat = etat;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JobOffre getJobOffre() {
        return jobOffre;
    }

    public void setJobOffre(JobOffre jobOffre) {
        this.jobOffre = jobOffre;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

}
