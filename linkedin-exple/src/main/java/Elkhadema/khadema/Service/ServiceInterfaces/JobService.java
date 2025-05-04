package Elkhadema.khadema.Service.ServiceInterfaces;

import java.util.List;

import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.JobOffre;
import Elkhadema.khadema.domain.JobRequest;
import Elkhadema.khadema.domain.SavedJob;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.Exception.NotCompanyException;
import Elkhadema.khadema.util.Exception.NotUserException;

public interface JobService {
    void addJob(JobOffre job) throws NotCompanyException;
    void deleteJob(JobOffre job) throws NotCompanyException;
    void editJob(JobOffre job,JobOffre newJobOffre) throws NotCompanyException;
    List<JobOffre> getAllJobOffresByCompany(Company company);
    List<JobRequest> getAllJobRequestByCompany(Company company);
    List<JobRequest> getAllJobRequestByUser(User user);
    List<SavedJob> getAllJobSavedByUser(User user);
    public void addSavedJob(JobOffre jobOffre)throws NotUserException;





}
