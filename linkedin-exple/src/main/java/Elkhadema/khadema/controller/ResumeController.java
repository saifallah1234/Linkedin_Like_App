package Elkhadema.khadema.controller;

import java.io.IOException;
import java.util.List;

import Elkhadema.khadema.App;
import Elkhadema.khadema.DAO.DAOImplemantation.CompetanceDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.ExperienceDAO;
import Elkhadema.khadema.Service.ServiceImplemantation.AdminServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.FollowServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.GenerateCVServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.UserServiceImp;
import Elkhadema.khadema.Service.ServiceInterfaces.AdminService;
import Elkhadema.khadema.Service.ServiceInterfaces.FollowService;
import Elkhadema.khadema.Service.ServiceInterfaces.GenerateCVService;
import Elkhadema.khadema.Service.ServiceInterfaces.UserService;
import Elkhadema.khadema.domain.Competance;
import Elkhadema.khadema.domain.Experience;
import Elkhadema.khadema.domain.Media;
import Elkhadema.khadema.domain.Person;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.MediaChooser;
import Elkhadema.khadema.util.Session;
import Elkhadema.khadema.util.Exception.UserNotFoundException;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ResumeController extends NavbarController {
    User session = Session.getUser();
    Person currentUser;

    UserService userService = new UserServiceImp();
    ExperienceDAO experienceDAO = new ExperienceDAO();
    CompetanceDAO competanceDAO = new CompetanceDAO();
    FollowService followService =new FollowServiceImp();
    GenerateCVService cvService = new GenerateCVServiceImp();
    AdminService adminService=new AdminServiceImp();

    @FXML
    ImageView profileImg;
    @FXML
    Text nameText;
    @FXML
    HBox btnVbox;
    @FXML
    TextField ageText;
    @FXML
    TextField sexeText;
    @FXML
    TextField jobText;
    @FXML
    VBox experienceVBox;
    @FXML
    VBox competanceVBox;

    @FXML
    Button changeImgbtn;
    @FXML
    Button addExperiancebtn;
    @FXML
    Button addSkillsbtn;
    @FXML
    TextArea aboutTextArea;
    @FXML
    Button editBioBtn;
    @FXML
    Button editAboutBtn;
    @FXML
    Button generateCVbutton;
    @FXML void confirmEdit() {

    }
    @FXML
    void cancelEdit() {

	}


    @FXML
    public void postMsg() {
        //ignore
    }

    @FXML
    private Button banButton;

    @FXML
    public void ban() {
        // TODO later
    }
    @FXML
    public void init(User user) {
        Person person = ((Person)userService.getUserById(user));

        currentUser = person;
        if (person.getId() != session.getId()) {
            Button followbutton = getFollowbutton();
            Button chatButton = getChatButton();
            chatButton.getStyleClass().add("postButton");
            btnVbox.getChildren().addAll(followbutton, chatButton);
            changeImgbtn.setDisable(true);
            changeImgbtn.setVisible(false);
            editBioBtn.setDisable(true);
            editBioBtn.setVisible(false);
            editBioBtn.setDisable(true);
            editBioBtn.setVisible(false);
            editAboutBtn.setDisable(true);
            editAboutBtn.setVisible(false);
            addExperiancebtn.setDisable(true);
            addExperiancebtn.setVisible(false);
            addSkillsbtn.setDisable(true);
            addSkillsbtn.setVisible(false);
            if (adminService.isAdmin(session)) {
                banButton.setVisible(true);
                banButton.setDisable(false);
            }
        }
        nameText.setText(person.getUserName());
        profileImg.setImage(person.getPhoto().getImage());
        profileImg.getStyleClass().add("round-image");
        generateCVbutton.setOnAction(event -> {
            try {
                cvService.generateCV(person);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        initContacts();
        afficheBio(person);
        afficheabout(person);
        List<Experience> experiences = experienceDAO.getAll(user);
        for (int i = 0; i < experiences.size() - 1; i++) {
            VBox experienceBox = new VBox();
            afficheExperience(experiences.get(i), experienceBox);
            experienceVBox.getChildren().add(experienceBox);
            Separator separator = new Separator();
            experienceVBox.getChildren().add(separator);
        }
        if (experiences.size() > 0) {
            VBox experienceBox = new VBox();
            afficheExperience(experiences.get(experiences.size() - 1), experienceBox);
            experienceVBox.getChildren().add(experienceBox);
        }
        List<Competance> competances = competanceDAO.getAll(user);
        for (int i = 0; i < competances.size() - 1; i++) {
            VBox competanceBox = new VBox();
            afficheCompetance(competances.get(i), competanceBox);
            competanceVBox.getChildren().add(competanceBox);
            Separator separator = new Separator();
            competanceVBox.getChildren().add(separator);
        }
        if (competances.size() > 0) {
            VBox competanceBox = new VBox();
            afficheCompetance(competances.get(competances.size() - 1), competanceBox);
            competanceVBox.getChildren().add(competanceBox);
        }
        // event
        aboutTextArea.setOnKeyPressed(event -> {
            if (event.getCode().isWhitespaceKey() && !event.isShiftDown()) {
                person.setAbout(aboutTextArea.getText());
                try {
                    userService.EditUser(person, person);
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                }
                aboutTextArea.setDisable(true);
            }
        });
        aboutTextArea.focusedProperty().addListener((observale, oldValue, newValue) -> {
            if (!newValue) {
                person.setAbout(aboutTextArea.getText());
                try {
                    userService.EditUser(person, person);
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                }
                aboutTextArea.setDisable(true);
            }
        });
    }

    private Button getChatButton() {
        Button chatButton = new Button("chat");
        chatButton.getStyleClass().add("postButton");
        chatButton.setOnAction(event -> {
            try {
                goChat(event, currentUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return chatButton;
    }

    private Button getFollowbutton() {
        Button followbutton = new Button("follow");
        if (followService.isFollowing(session, currentUser)) {
            followbutton.setText("unfollow");
        }
        followbutton.getStyleClass().add("postButton");
        followbutton.setOnAction(event -> {
            if (followService.isFollowing(session, currentUser)) {
                followService.Follow(session, currentUser);
                followbutton.setText("unfollow");
            } else {
                followService.unFollow(session, currentUser);
                followbutton.setText("follow");
            }
        });
        return followbutton;
    }

    private void afficheBio(Person person) {
        ageText.setText(String.valueOf(person.getAge()));
        jobText.setText(person.getJob());
        sexeText.setText(person.getSexe());
        sexeText.setDisable(true);
        ageText.setDisable(true);
        jobText.setDisable(true);
        ageText.getStyleClass().add("disabled-text");
        jobText.getStyleClass().add("disabled-text");
        sexeText.getStyleClass().add("disabled-text");
    }

    private void afficheabout(Person person) {
        if (person.getAbout() == null) {
            aboutTextArea.setText("");

        }
        aboutTextArea.setText(person.getAbout());
    }

    private void afficheExperience(Experience experience, VBox vBox) {
        Text technologieText = new Text(experience.getTechnologie());
        technologieText.setFont(Font.font("SansSerif", 18));
        technologieText.setFill(Color.WHITE);
        HBox titleBox = new HBox(technologieText);
        Text missionText = new Text(experience.getMission() + " · " + experience.getType());
        missionText.setFill(Color.WHITE);
        missionText.setFont(Font.font("SansSerif", 14));

        Text dateText = new Text(experience.getDateExperience());
        dateText.setFont(Font.font("SansSerif", 14));
        dateText.setFill(Color.WHITE);

        TextArea descriptionArea = new TextArea(experience.getDescription());
        descriptionArea.getStyleClass().add("postTxtField");
        VBox innerVBox = new VBox(titleBox, missionText, dateText);
        if (currentUser.getId() == session.getId()) {
            addEditButtonExperience(titleBox, experience);
        }

        vBox.getChildren().add(innerVBox);
    }

    private void afficheCompetance(Competance competance, VBox competanceBox) {
        Text technologieText = new Text(competance.getTechnologie());
        technologieText.setFont(Font.font("SansSerif", 14));
        technologieText.setFill(Color.WHITE);

        VBox innerVBox = new VBox();
        if (currentUser.getId() == session.getId()) {
            addEditButtonCompetance(innerVBox, competance);
        } else {
            Text titreText = new Text(competance.getTitre());
            titreText.setFont(Font.font("SansSerif", 18));
            titreText.setFill(Color.WHITE);
            innerVBox.getChildren().add(titreText);
        }
        innerVBox.getChildren().add(technologieText);
        competanceVBox.getChildren().add(innerVBox);
    }

    private void addEditButtonCompetance(VBox vBox, Competance competance) {
        Button editButton = new Button("🖉");
        editButton.getStyleClass().add("postButton");
        HBox hBox = new HBox();
        Text titreText = new Text(competance.getTitre());
        titreText.setFont(Font.font("SansSerif", 18));
        titreText.setFill(Color.WHITE);
        hBox.getChildren().addAll(titreText, editButton);
        vBox.getChildren().add(hBox);
        editButton.setOnAction(ActionEvent -> {
            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            popUpStage.setTitle("Edit competance");
            FXMLLoader loader = new FXMLLoader(App.class.getResource("editCompetance.fxml"));
            Scene popUpScreen = new Scene(new Pane());
            try {
                popUpScreen = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            EditCompetanceController editCompetanceController = loader.getController();
            popUpStage.setScene(popUpScreen);
            editCompetanceController.setStage(popUpStage);
            editCompetanceController.initialize(competance);
            popUpStage.showAndWait();
            Competance newCompetance = editCompetanceController.getCompetance();
            vBox.getChildren().clear();
            afficheCompetance(competance, vBox);
            competanceDAO.update(competance, newCompetance);
        });
    }

    private void addEditButtonExperience(HBox hBox, Experience experience) {
        Button editButton = new Button("🖉");
        editButton.getStyleClass().add("postButton");
        hBox.getChildren().add(editButton);
        editButton.setOnAction(ActionEvent -> {
            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            popUpStage.setTitle("Edit experience");
            FXMLLoader loader = new FXMLLoader(App.class.getResource("editExperience.fxml"));
            Scene popUpScreen = new Scene(new Pane());
            try {
                popUpScreen = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            EditExperienceController editExperienceController = loader.getController();
            popUpStage.setScene(popUpScreen);
            editExperienceController.setStage(popUpStage);
            editExperienceController.initialize(experience);

            popUpStage.showAndWait();

            Experience newExperience = editExperienceController.getExperience();
            if (editExperienceController.choix == false) {
                return;
            }
            VBox parentBox = ((VBox) ((VBox) hBox.getParent()));
            parentBox.getChildren().clear();
            afficheExperience(newExperience, parentBox);
            experienceDAO.update(experience, newExperience);
        });
    }

    @FXML
    public void addExperience() throws IOException {
        VBox vBox = new VBox();
        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setTitle("Edit Bio");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("editExperience.fxml"));
        Scene popUpScreen = new Scene(loader.load());
        EditExperienceController editExperienceController = loader.getController();
        popUpStage.setScene(popUpScreen);
        editExperienceController.setStage(popUpStage);
        Experience experience = null;
        editExperienceController.initialize(experience);
        popUpStage.showAndWait();
        if (editExperienceController.choix == false) {
            return;
        }
        experience = editExperienceController.getExperience();
        Separator separator = new Separator();
        afficheExperience(experience, vBox);
        experienceDAO.save(experience, currentUser);
        experienceVBox.getChildren().addAll(separator,vBox);
    }

    @FXML
    public void addCompetance() throws IOException {
        VBox vBox = new VBox();
        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setTitle("Edit Bio");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("editCompetance.fxml"));
        Scene popUpScreen = new Scene(loader.load());
        EditCompetanceController editCompetanceController = loader.getController();
        popUpStage.setScene(popUpScreen);
        editCompetanceController.setStage(popUpStage);
        Competance competance = null;
        editCompetanceController.initialize(competance);
        popUpStage.showAndWait();
        if (editCompetanceController.choix == false) {
            return;
        }
        competance = editCompetanceController.getCompetance();
        afficheCompetance(competance, vBox);
        Separator separator = new Separator();
        competanceDAO.save(competance, currentUser);
        competanceVBox.getChildren().addAll(separator,vBox);
    }

    @FXML
    void addImage(ActionEvent event) throws UserNotFoundException {
        Media m = MediaChooser.Choose(event);
        if (m.getImage() == null) {
            profileImg.setImage(new Image("user.jpg.png"));
            return;
        }
        if (!m.getMediatype().equals("img")) {
            return;
        }
        profileImg.setImage(m.getImage());
        currentUser.setPhoto(m);
        userService.EditUser(currentUser, currentUser);
    }

    @FXML
    private void editBio() throws IOException, UserNotFoundException {
        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setTitle("Edit Bio");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("editBio.fxml"));
        Scene popUpScreen = new Scene(loader.load());
        EditBioController editBioController = loader.getController();
        popUpStage.setScene(popUpScreen);
        editBioController.setStage(popUpStage);
        editBioController.initialize(currentUser);
        popUpStage.showAndWait();
        if (editBioController.isClosedHow() == false) {
            return;
        }
        currentUser.setAge(editBioController.getAge());
        currentUser.setJob(editBioController.getJob());
        currentUser.setSexe(editBioController.getSexe());
        userService.EditUser(currentUser, currentUser);
        afficheBio(currentUser);
    }

    @FXML
    private void editAbout() {
        aboutTextArea.setDisable(false);

    }
}
