package Elkhadema.khadema.controller;

import java.io.IOException;
import Elkhadema.khadema.App;
import Elkhadema.khadema.Service.ServiceImplemantation.UserServiceImp;
import Elkhadema.khadema.Service.ServiceInterfaces.UserService;
import Elkhadema.khadema.domain.ContactInfo;
import Elkhadema.khadema.domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {
    // new user to add
    User user = new User(0, null, null);
    ContactInfo contactInfo = new ContactInfo(0);
    // service
    UserService userService = new UserServiceImp();
    // content holder
    @FXML
    TextField name;
    @FXML
    TextField password;
    @FXML
    Text invalid;
    @FXML
    public void login() throws IOException{
        try {
            user= userService.Login(name.getText(), password.getText());

        } catch (Exception e) {
            invalid.setText("username n'existe pas");
            return;
        }
        if (user==null) {
            invalid.setText("password invalid");
        }

        App.setRoot("mainpage");
    }

    @FXML
    public void signUp() throws IOException{
        App.setRoot("ChooseAccount");
    }
}
