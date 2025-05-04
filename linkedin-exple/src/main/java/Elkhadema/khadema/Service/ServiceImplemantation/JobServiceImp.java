package Elkhadema.khadema.Service.ServiceImplemantation;

import java.util.List;
import java.util.stream.Collectors;

import Elkhadema.khadema.DAO.DAOImplemantation.JobsDAO;
import Elkhadema.khadema.Service.ServiceInterfaces.CompanyService;
import Elkhadema.khadema.Service.ServiceInterfaces.JobService;
import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.JobOffre;
import Elkhadema.khadema.domain.JobRequest;
import Elkhadema.khadema.domain.SavedJob;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.Session;
import Elkhadema.khadema.util.Exception.NotCompanyException;
import Elkhadema.khadema.util.Exception.NotUserException;

public class JobServiceImp implements JobService{
    JobsDAO jobsDAO=new JobsDAO();
    CompanyService companyService=new CompanyServiceImp();

    @Override
    public void addJob(JobOffre job) throws NotCompanyException {
        if (!companyService.isCompany(Session.getUser())) {
            throw new NotCompanyException();
        }
        jobsDAO.saveJobOffre(job);
    }
    @Override
    public void addSavedJob(JobOffre jobOffre)throws NotUserException{
        if (companyService.isCompany(Session.getUser())) {
            throw new NotUserException();
        }
        jobsDAO.saveJob(new SavedJob(Session.getUser(), jobOffre));
    }
    @Override
    public void deleteJob(JobOffre job) throws NotCompanyException {
        if (!companyService.isCompany(Session.getUser())) {
            throw new NotCompanyException();
        }
        jobsDAO.deleteJobOffer(job);
    }

    @Override
    public void editJob(JobOffre job,JobOffre newJobOffre) throws NotCompanyException {
        if (!companyService.isCompany(Session.getUser())) {
            throw new NotCompanyException();
        }
        jobsDAO.updateJobOffre(job, newJobOffre);
    }

    @Override
    public List<JobOffre> getAllJobOffresByCompany(Company company) {
        return jobsDAO.getAllJobOffresByCompany(company);
    }

    @Override
    public List<JobRequest> getAllJobRequestByCompany(Company company) {
        return jobsDAO.getJobRequestsByCompany(company);
    }

    @Override
    public List<JobRequest> getAllJobRequestByUser(User user) {
        return jobsDAO.getJobRequestsByUser(user);
    }

    @Override
    public List<SavedJob> getAllJobSavedByUser(User user) {
        return jobsDAO.getSavedJobsByUser(user);
    }
    public List<JobOffre> getAllJobOffres() {
    	List<JobOffre> jobOffres=jobsDAO.getAllJobOffres().stream().map(t -> {t.setCompany(companyService.getCompanyInfo(t.getCompany()));return t;}).collect(Collectors.toList());
    	return jobOffres;
		
	}
}
