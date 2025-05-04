package Elkhadema.khadema.domain;

public class SavedJob {
    private User user;
    private JobOffre jobOffre;

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

    public SavedJob(User user, JobOffre jobOffre) {
        this.user = user;
        this.jobOffre = jobOffre;
    };

}
