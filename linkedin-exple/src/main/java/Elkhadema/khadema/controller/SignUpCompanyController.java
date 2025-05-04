package Elkhadema.khadema.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import Elkhadema.khadema.App;
import Elkhadema.khadema.Service.ServiceImplemantation.UserServiceImp;
import Elkhadema.khadema.Service.validateInfo.EmailValidator;
import Elkhadema.khadema.Service.validateInfo.PasswordValidator;
import Elkhadema.khadema.Service.validateInfo.UrlValidator;
import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.ContactInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class SignUpCompanyController implements Initializable {
    private UserServiceImp userService = new UserServiceImp();

    private Company company = new Company(0, null, null);
    private ContactInfo contactInfo = new ContactInfo(0);

    @FXML
    private TextField textName;
    @FXML
    private TextField textEmail;
    @FXML
    private PasswordField textPassword;
    @FXML
    private PasswordField textConfirm;
    @FXML
    private TextArea Textdescription;
    @FXML
    private ToggleGroup type;
    @FXML
    private RadioButton publ, pri;
    @FXML
    private TextField textSite;
    @FXML
    private DatePicker creationDatePicker;
    @FXML
    private ComboBox<String> industry;
    @FXML
    private Text invalid;
    @FXML
    private TextField location;

    @FXML
    public void chooseAccount() throws IOException {
        App.setRoot("ChooseAccount");
    }

    @FXML
    public void login() throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void signUp() throws IOException {
        String name = textName.getText();
        if (name=="") {
            invalid.setText("name empty");
            return;
        }
        company.setUserName(name);
        company.setCompanyName(name);

        String email = textEmail.getText();
        if (!EmailValidator.isValidEmail(email)) {
            invalid.setText("* email invalid ");
            return;
        }
        contactInfo.setEmail(email);
        String password = textPassword.getText();

        if (PasswordValidator.isValidPassword(password)) {
            invalid.setText("* password invalid");
            return;
        }
        String confirm = textConfirm.getText();
        if (!confirm.equals(password)) {
            invalid.setText("confirm and your password don't match");
            return;
        }
        company.setPassword(password);
        String description = Textdescription.getText();
        company.setDescription(description);
        if (publ.isSelected()) {
            company.setSpeciality("public");
        } else {
            if (pri.isSelected()) {
                company.setSpeciality("privee");
            } else {
                invalid.setText("choose a type");
                return;
            }
        }
        LocalDate creationDate = creationDatePicker.getValue();
        if (creationDate.isAfter(LocalDate.now())) {
            invalid.setText("date invalid");
        }
        if(location.getText()==null){
            invalid.setText("choose an location");
              return;}
        company.setAddress(location.getText());
        ZoneId defaultZoneId = ZoneId.systemDefault();
        company.setCreationDate(Date.from(creationDate.atStartOfDay(defaultZoneId).toInstant()));
        if(industry.getValue()==null){
          invalid.setText("choose an industry");
          return;}
          company.setIndustry(industry.getValue());
          if (!UrlValidator.validateURL(textSite.getText())) {
            invalid.setText("website invalid");
            return;

        }
        company.setWebsite(description);
        company.setContactInfo(contactInfo);
        userService.SignUpCompany(company);
        App.setRoot("login");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ObservableList<String> industriesObservableList = FXCollections.observableArrayList();
        String[] industriesArray = {
                "Technology",
                "Finance",
                "Healthcare",
                "Retail",
                "Manufacturing",
                "Energy",
                "Hospitality and Tourism",
                "Transportation and Logistics",
                "Real Estate",
                "Education",
                "Media and Entertainment",
                "Professional Services",
                "Consumer Services",
                "Agriculture",
                "Government and Public Sector",
                "Nonprofit and Social Services",
                "Environmental Services",
                "Research and Development",
                "Construction and Engineering",
                "Legal and Compliance"
        };
        industriesObservableList.setAll(industriesArray);
        industry.setItems(industriesObservableList);
    }

}
