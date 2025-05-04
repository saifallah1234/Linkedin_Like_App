package Elkhadema.khadema.Service.ServiceImplemantation;

import Elkhadema.khadema.DAO.DAOImplemantation.CompanyDAO;
import Elkhadema.khadema.Service.ServiceInterfaces.CompanyService;
import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.User;

public class CompanyServiceImp implements CompanyService {
    CompanyDAO companyDAO = new CompanyDAO();

    @Override
    public boolean isCompany(User user) {
        return companyDAO.get(user.getId()).isPresent();
    }

    @Override
    public Company showProfile(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showProfile'");
    }

    @Override
    public Company getCompanyInfo(Company company) {
        return companyDAO.get(company.getId()).get();
    }

}
