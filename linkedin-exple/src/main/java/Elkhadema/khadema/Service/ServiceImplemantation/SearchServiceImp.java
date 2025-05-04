package Elkhadema.khadema.Service.ServiceImplemantation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import Elkhadema.khadema.DAO.DAOImplemantation.CompanyDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.JobsDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.PostDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.UserDAO;
import Elkhadema.khadema.Service.ServiceInterfaces.SearchService;
import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.JobOffre;
import Elkhadema.khadema.domain.Post;
import Elkhadema.khadema.domain.User;

public class SearchServiceImp implements SearchService {
    UserDAO userDAO = new UserDAO();
    PostDAO postDAO = new PostDAO();
    CompanyDAO companyDAO = new CompanyDAO();
    JobsDAO jobsDAO = new JobsDAO();

    @Override
    public Set<Object> search(String searchWord) {
        Set<Object> itemList = new HashSet<>();
        itemList.addAll(searchByCompanies(searchWord));
        itemList.addAll(searchByJobOffres(searchWord));
        itemList.addAll(searchByPosts(searchWord));
        itemList.addAll(searchByUsers(searchWord));
        return itemList;

    }

    @Override
    public Set<User> searchByUsers(String searchWord) {
        Set<User> usersSet = userDAO.getAll().stream()
                .filter(user -> user.getUserName().contains(searchWord))
                .collect(Collectors.toSet());
        return usersSet;
    }

    @Override
    public Set<Post> searchByPosts(String searchWord) {
        Set<Post> postsSet = postDAO.getAllPostsUnderParent(0).stream()
                .filter(post -> post.getContent().contains(searchWord))
                .collect(Collectors.toSet());
        return postsSet;

    }

    @Override
    public Set<Company> searchByCompanies(String searchWord) {
        Set<Company> companies = companyDAO.getAll().stream()
                .filter(company -> company.getCompanyName().contains(searchWord))
                .collect(Collectors.toSet());
        return companies;
    }

    @Override
    public Set<JobOffre> searchByJobOffres(String searchWord) {
        Set<JobOffre> jobOffres = companyDAO.getAll().stream()
                .flatMap(company -> jobsDAO.getAllJobOffresByCompany(company).stream())
                .filter(JobOffre -> JobOffre.getPostion().contains(searchWord)).collect(Collectors.toSet());
        return jobOffres;
    }

}
