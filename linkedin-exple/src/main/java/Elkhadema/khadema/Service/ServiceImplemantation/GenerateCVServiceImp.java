package Elkhadema.khadema.Service.ServiceImplemantation;

import java.io.IOException;

import Elkhadema.khadema.DAO.DAOImplemantation.CompetanceDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.ContactInfoDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.ExperienceDAO;
import Elkhadema.khadema.Service.ServiceInterfaces.GenerateCVService;
import Elkhadema.khadema.domain.Person;
import Elkhadema.khadema.util.CVgenerator;

public class GenerateCVServiceImp implements GenerateCVService {
    private ExperienceDAO experienceDAO = new ExperienceDAO();
    private CompetanceDAO competanceDAO = new CompetanceDAO();
    private ContactInfoDAO contactInfoDAO = new ContactInfoDAO();

    @Override
    public void generateCV(Person person) throws IOException {
        person.setContactInfo(contactInfoDAO.get(person.getContactInfo().getId()).get());
        CVgenerator.Generate(person, "CV.pdf", competanceDAO.getAll(person),
                experienceDAO.getAll(person));
    }

}
