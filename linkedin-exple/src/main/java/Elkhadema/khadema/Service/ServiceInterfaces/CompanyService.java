package Elkhadema.khadema.Service.ServiceInterfaces;

import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.User;

public interface CompanyService {
    boolean isCompany(User user);

    Company showProfile(User user);
    Company getCompanyInfo(Company company);

}
