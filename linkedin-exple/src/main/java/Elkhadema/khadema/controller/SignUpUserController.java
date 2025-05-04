package Elkhadema.khadema.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import Elkhadema.khadema.App;
import Elkhadema.khadema.Service.ServiceImplemantation.UserServiceImp;
import Elkhadema.khadema.Service.validateInfo.EmailValidator;
import Elkhadema.khadema.Service.validateInfo.PasswordValidator;
import Elkhadema.khadema.Service.validateInfo.PhoneNumberValidate;
import Elkhadema.khadema.domain.ContactInfo;
import Elkhadema.khadema.domain.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class SignUpUserController implements Initializable {
    UserServiceImp userService = new UserServiceImp();

    Person user = new Person(0, null, null);
    ContactInfo contactInfo = new ContactInfo(0);
    @FXML
    TextField firstname;
    @FXML
    TextField lastname;
    @FXML
    TextField email;
    @FXML
    TextField code;
    @FXML
    TextField phone;
    @FXML
    TextField password;
    @FXML
    TextField confirm;
    @FXML
    Text invalid;
    @FXML
    private TextField job;
    @FXML
    ComboBox<String> country;
    @FXML
    private ScrollPane CC;
    @FXML
    private RadioButton femalebtn;
    @FXML
    private RadioButton malebtn;
    @FXML
    private TextArea profession;
    @FXML
    private TextField age;

    @FXML
    private TextField Username;

    @FXML
    public void chooseAccount() throws IOException {
        App.setRoot("ChooseAccount");
    }
    @FXML
    public void register() throws IOException {
        user.setContactInfo(contactInfo);
        String vemail = email.getText();
        if (!EmailValidator.isValidEmail(vemail)) {
            invalid.setText("*email invalid");
            return;
        }
        contactInfo.setEmail(vemail);
        if (firstname.getText().length()<2) {
            invalid.setText("*firstname invalid");
            return;
        }
        if (lastname.getText().length()<2) {
            invalid.setText("*lastname invalid");
            return;
        }
        System.out.println(Username.getText());
        if (Username.getText().length()<2) {
            invalid.setText("*username invalid");
            return;
        }
        user.setFirstName(firstname.getText());
        user.setLastName(lastname.getText());
        user.setUserName(Username.getText());
        if(country.getValue()==null){
            invalid.setText("choose a country");
            return;
        }
        contactInfo.setAddress(country.getValue());
        if (PasswordValidator.isValidPassword(password.getText())) {
            invalid.setText("password invalid");
            return;
        }
        String confirmPassword = confirm.getText();
        if (!confirmPassword.equals(password.getText())) {
            invalid.setText("confirm your password");
            return;
        }
        user.setPassword(confirmPassword);
        String phoneNumber = phone.getText();
        if (!PhoneNumberValidate.isValidPhoneNumber(phoneNumber)) {
            invalid.setText("phone number invalid");
            return;
        }
        String agee=age.getText();
        if (!agee.matches("\\d+")) {
			invalid.setText("Age invalide");
			return;
		}
        user.setAge(Integer.parseInt(agee));
        String prof=profession.getText();
        if (prof.length()<2) {
			invalid.setText("Age invalide");
			return;
		}
        if (malebtn.isSelected()) {
			user.setSexe("male");
		}
        else if (femalebtn.isSelected()) {
			user.setSexe("female");
		}
        else {
        	invalid.setText("invalid gender");
			return;
		}
        if(job.getText().length()<2) {
        	invalid.setText("invalid job");
        	return;
        }
        user.setJob(job.getText());
        user.setAbout(prof);
        contactInfo.setPhoneNumber(Integer.parseInt(phoneNumber));
        this.userService.SignUpPerson(user);
        App.setRoot("login");
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	ToggleGroup toggleGroup = new ToggleGroup();
        femalebtn.setToggleGroup(toggleGroup);
        malebtn.setToggleGroup(toggleGroup);
        ObservableList<String> countries=FXCollections.observableArrayList();
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            countries.add( locale.getDisplayCountry());
        }
        country.setPromptText("choose a country");
        country.setItems(countries);
    }


}
