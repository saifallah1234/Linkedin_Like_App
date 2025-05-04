package Elkhadema.khadema.Service.ServiceInterfaces;

import java.util.Set;

import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.JobOffre;
import Elkhadema.khadema.domain.Post;
import Elkhadema.khadema.domain.User;

public interface SearchService {
    Set<Object> search(String searchWord);

    Set<User> searchByUsers(String searchWord);

    Set<Post> searchByPosts(String searchWord);

    Set<Company> searchByCompanies(String searchWord);

    Set<JobOffre> searchByJobOffres(String searchWord);

}
